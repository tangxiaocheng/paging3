package app.sram.bikestore.paging.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GetMoviesRxViewModelFactory(private val repository: GetDataRepo) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetMoviesRxViewModel::class.java)) {
            return GetMoviesRxViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
