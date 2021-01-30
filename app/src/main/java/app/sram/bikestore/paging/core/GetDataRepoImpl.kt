package app.sram.bikestore.paging.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import app.sram.bikestore.paging.dao.BikeStoresDao
import app.sram.bikestore.paging.dao.Movies
import io.reactivex.Flowable
import javax.inject.Inject

class GetDataRepoImpl @Inject constructor(
    private val moviesRxDao: BikeStoresDao,
    private val remoteMediator: RxRemoteMediator,
    private val config: PagingConfig
) : GetDataRepo {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingDataList(): Flowable<PagingData<Movies.Movie>> {

        return Pager(
            config = config,
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                moviesRxDao.selectAll()
            }
        ).flowable
    }
}
