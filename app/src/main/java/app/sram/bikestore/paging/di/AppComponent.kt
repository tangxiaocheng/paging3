package app.sram.bikestore.paging.di

import app.sram.bikestore.paging.MovieRxRemoteFragment
import app.sram.bikestore.paging.PagingActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@AppScope
@Component(modules = [RoomModule::class, AppConfigModule::class, ApiModule::class, ImpModule::class])
interface AppComponent {
    fun inject(activity: PagingActivity)
    fun inject(movieRxRemoteFragment: MovieRxRemoteFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appConfig(config: AppConfig): Builder
        fun build(): AppComponent
    }
}
