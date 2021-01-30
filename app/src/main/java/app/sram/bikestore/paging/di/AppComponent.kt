package app.sram.bikestore.paging.di

import app.sram.bikestore.paging.PagingActivity
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {
    fun inject(activity: PagingActivity)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appConfig(config: AppConfig): Builder
        fun build(): AppComponent
    }
}