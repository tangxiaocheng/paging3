package app.sram.bikestore.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import app.sram.bikestore.paging.Movies
import app.sram.bikestore.paging.GetMoviesRxRepository
import io.reactivex.Flowable

class GetMoviesRxViewModel(private val repository: GetMoviesRxRepository) : ViewModel() {
    fun getFavoriteMovies(): Flowable<PagingData<Movies.Movie>> {
        return repository
            .getMovies()
            .map { pagingData -> pagingData.filter { it.poster != null } }
            .cachedIn(viewModelScope)
    }
}