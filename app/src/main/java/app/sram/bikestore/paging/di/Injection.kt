package app.sram.bikestore.paging.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import app.sram.bikestore.paging.api.RestApi
import app.sram.bikestore.paging.core.GetDataRepoImpl
import app.sram.bikestore.paging.core.GetMoviesRxViewModelFactory
import app.sram.bikestore.paging.core.RxRemoteMediator
import app.sram.bikestore.paging.dao.AppDatabase
import app.sram.bikestore.paging.data.MoviesMapper

object Injection {
    private fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    fun provideRxRemoteViewModel(context: Context): ViewModelProvider.Factory {
        val remoteMediator =
            RxRemoteMediator(
                service = RestApi.create(),
                database = provideDatabase(context),
                mapper = MoviesMapper()
            )

        val repository =
            GetDataRepoImpl(
                database = provideDatabase(context),
                remoteMediator = remoteMediator
            )

        return GetMoviesRxViewModelFactory(repository)
    }
}