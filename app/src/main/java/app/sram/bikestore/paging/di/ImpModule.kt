package app.sram.bikestore.paging.di

import app.sram.bikestore.paging.core.GetDataRepo
import app.sram.bikestore.paging.core.GetDataRepoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ImpModule {

    @Binds
    @AppScope
    abstract fun bindRepoStateList(impl: GetDataRepoImpl): GetDataRepo
}
