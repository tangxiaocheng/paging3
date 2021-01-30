package app.sram.bikestore.paging.di

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.sram.bikestore.paging.core.AppRxRemoteMediator
import app.sram.bikestore.paging.dao.BikeStoresDao
import app.sram.bikestore.paging.dao.MoviesDb
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl

@Module
class AppConfigModule {
    @Provides
    fun provideServerUrl(appConfig: AppConfig): HttpUrl {
        return appConfig.serverUrl
    }

    /**
     * Expose Application rather than context to avoid confusion from Activity Context.
     * Any dependencies that requests Application context could just use the Application in AppComponent
     * */
    @Provides
    fun provideApplication(appConfig: AppConfig): Application {
        return appConfig.application
    }

    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 40
        )
    }

    @ExperimentalPagingApi
    @Provides
    fun providePager(
        config: PagingConfig,
        remoteMediatorApp: AppRxRemoteMediator,
        moviesRxDao: BikeStoresDao
    ): Pager<Int, MoviesDb.MovieEntity> {
        return Pager(
            config = config,
            remoteMediator = remoteMediatorApp,
            pagingSourceFactory = {
                moviesRxDao.selectAll()
            }
        )
    }
}
