package app.sram.bikestore.paging

import androidx.paging.PagingData
import app.sram.bikestore.paging.Movies
import io.reactivex.Flowable

interface GetMoviesRxRepository {
    fun getMovies(): Flowable<PagingData<Movies.Movie>>
}