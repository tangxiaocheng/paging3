package app.sram.bikestore.paging.core

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import app.sram.bikestore.paging.api.RestApi
import app.sram.bikestore.paging.dao.AppDatabase
import app.sram.bikestore.paging.dao.BikeStoresDao
import app.sram.bikestore.paging.dao.MoviesDb
import app.sram.bikestore.paging.dao.RemoteKeysDao
import app.sram.bikestore.paging.data.MoviesMapper
import app.sram.bikestore.paging.data.MoviesResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AppRxRemoteMediator @Inject constructor(
    private val restApi: RestApi,
    private val database: AppDatabase,
    private val mapper: MoviesMapper,
    private val remoteKeysDao: RemoteKeysDao,
    private val bikeStoresDao: BikeStoresDao
) : RxRemoteMediator<Int, MoviesDb.MovieEntity>() {
    override fun loadSingle(loadType: LoadType, state: PagingState<Int, MoviesDb.MovieEntity>): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map { loadTypeHere: LoadType ->
                when (loadTypeHere) {
                    LoadType.REFRESH -> {
                        val remoteKeys: MoviesDb.MovieRemoteKeys? = getRemoteKeyClosestToCurrentPosition(state)
                        ""
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevPageToken ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextPageToken ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page: String ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    dealSingle(page, loadType)
                }
            }
            .onErrorReturn { throwable: Throwable ->
                throwable.printStackTrace()
                Log.d("Randy", "loadSingle:${throwable.printStackTrace()}")
                MediatorResult.Error(throwable)
            }
    }

    private fun dealSingle(
        pageTokenString: String?,
        loadType: LoadType
    ): Single<MediatorResult> {
        return restApi.popularMovieRx(pageToken = pageTokenString ?: "")
            .map { response: MoviesResponse -> map2moviesDb(response) }
            .map { moviesDb: MoviesDb -> insertToDb(loadType, moviesDb, pageTokenString) }
            .map<MediatorResult> { moviesDb: MoviesDb -> MediatorResult.Success(endOfPaginationReached = moviesDb.endOfPage) }
            .onErrorReturn { throwable: Throwable ->
                throwable.printStackTrace()
                Log.d("Randy", "popularMovieRx:${throwable.printStackTrace()}")
                MediatorResult.Error(throwable)
            }
    }

    private fun map2moviesDb(response: MoviesResponse): MoviesDb {
        return mapper.transform(response)
    }

    private fun insertToDb(loadType: LoadType, data: MoviesDb, prevPageTokenString: String?): MoviesDb {
        database.runInTransaction {
            if (loadType == LoadType.REFRESH) {
                remoteKeysDao.clearRemoteKeys()
                bikeStoresDao.clearAll()
            }
            val keys: List<MoviesDb.MovieRemoteKeys> = data.movieEntities.map {
                MoviesDb.MovieRemoteKeys(placeId = it.placeId, prevPageToken = prevPageTokenString, nextPageToken = data.pageToken)
            }
            remoteKeysDao.insertAll(keys)
            bikeStoresDao.insertAll(data.movieEntities)
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, MoviesDb.MovieEntity>): MoviesDb.MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            remoteKeysDao.remoteKeysByMovieId(repo.placeId)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, MoviesDb.MovieEntity>): MoviesDb.MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            remoteKeysDao.remoteKeysByMovieId(movie.placeId)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MoviesDb.MovieEntity>): MoviesDb.MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.placeId?.let { id ->
                remoteKeysDao.remoteKeysByMovieId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = "-1"
    }
}
