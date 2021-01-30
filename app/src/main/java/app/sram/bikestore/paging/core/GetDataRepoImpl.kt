package app.sram.bikestore.paging.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import app.sram.bikestore.paging.dao.MoviesDb
import io.reactivex.Flowable
import javax.inject.Inject

class GetDataRepoImpl @Inject constructor(
    private val pager: Pager<Int, MoviesDb.MovieEntity>
) : GetDataRepo {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingDataList(): Flowable<PagingData<MoviesDb.MovieEntity>> {
        return pager.flowable
    }
}
