package app.sram.bikestore.paging.core

import androidx.paging.PagingData
import app.sram.bikestore.paging.dao.Movies
import io.reactivex.Flowable

interface GetDataRepo {
    fun getPagingDataList(): Flowable<PagingData<Movies.Movie>>
}
