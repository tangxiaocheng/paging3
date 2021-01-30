package app.sram.bikestore.paging.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import app.sram.bikestore.paging.dao.MoviesDb
import io.reactivex.Flowable
import javax.inject.Inject

class GetMoviesRxViewModel @Inject constructor(private val repository: GetDataRepo) : ViewModel() {
    fun getFavoriteMovies(): Flowable<PagingData<MoviesDb.MovieEntity>> {
        return repository
            .getPagingDataList()
            .map { pagingData -> pagingData.filter { it.poster != null } }
            .cachedIn(viewModelScope)
    }
}
