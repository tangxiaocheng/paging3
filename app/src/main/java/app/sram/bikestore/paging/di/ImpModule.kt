package app.sram.bikestore.paging.di

import androidx.lifecycle.ViewModelProvider
import app.sram.bikestore.paging.core.GetDataRepo
import app.sram.bikestore.paging.core.GetDataRepoImpl
import app.sram.bikestore.paging.core.GetMoviesRxViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ImpModule {

    @Binds
    @AppScope
    abstract fun bindRepoStateList(impl: GetDataRepoImpl): GetDataRepo

    @Binds
    @AppScope
    abstract fun provideViewModelProviderFactory(repository: GetMoviesRxViewModelFactory): ViewModelProvider.Factory
}
