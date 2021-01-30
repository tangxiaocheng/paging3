package app.sram.bikestore.paging.core

import androidx.paging.PagingData
import app.sram.bikestore.paging.dao.MoviesDb
import io.reactivex.Flowable

interface GetDataRepo {
    fun getPagingDataList(): Flowable<PagingData<MoviesDb.MovieEntity>>
}
