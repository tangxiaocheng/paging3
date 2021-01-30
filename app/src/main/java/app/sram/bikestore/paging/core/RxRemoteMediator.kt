package app.sram.bikestore.paging.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import app.sram.bikestore.paging.api.RestApi
import app.sram.bikestore.paging.dao.AppDatabase
import app.sram.bikestore.paging.dao.BikeStoresDao
import app.sram.bikestore.paging.dao.Movies
import app.sram.bikestore.paging.dao.RemoteKeysDao
import app.sram.bikestore.paging.data.MoviesMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RxRemoteMediator @Inject constructor(
    private val service: RestApi,
    private val database: AppDatabase,
    private val mapper: MoviesMapper,
    private val remoteKeysDao: RemoteKeysDao,
    private val bikeStoresDao: BikeStoresDao
) : RxRemoteMediator<Int, Movies.Movie>() {
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Movies.Movie>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    service.popularMovieRx(page = page)
                        .map { mapper.transform(it) }
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.endOfPage) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }
    }

    private fun insertToDb(page: Int, loadType: LoadType, data: Movies): Movies {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                remoteKeysDao.clearRemoteKeys()
                bikeStoresDao.clearAll()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.movies.map {
                Movies.MovieRemoteKeys(movieId = it.movieId, prevKey = prevKey, nextKey = nextKey)
            }
            remoteKeysDao.insertAll(keys)
            bikeStoresDao.insertAll(data.movies)
            database.setTransactionSuccessful()
        } finally {
            database.endTransaction()
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            remoteKeysDao.remoteKeysByMovieId(repo.movieId)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            remoteKeysDao.remoteKeysByMovieId(movie.movieId)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                remoteKeysDao.remoteKeysByMovieId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}
