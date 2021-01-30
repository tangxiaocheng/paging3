package app.sram.bikestore.paging.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import app.sram.bikestore.paging.dao.Movies
import io.reactivex.Flowable
import javax.inject.Inject

class GetDataRepoImpl @Inject constructor(
    private val pager: Pager<Int, Movies.Movie>
) : GetDataRepo {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingDataList(): Flowable<PagingData<Movies.Movie>> {
        return pager.flowable
    }
}
