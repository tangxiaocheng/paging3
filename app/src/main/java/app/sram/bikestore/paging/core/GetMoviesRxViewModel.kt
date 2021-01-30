package app.sram.bikestore.paging.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import app.sram.bikestore.paging.dao.Movies
import io.reactivex.Flowable

class GetMoviesRxViewModel(private val repository: GetDataRepo) : ViewModel() {
    fun getFavoriteMovies(): Flowable<PagingData<Movies.Movie>> {
        return repository
            .getPagingDataList()
            .map { pagingData -> pagingData.filter { it.poster != null } }
            .cachedIn(viewModelScope)
    }
}