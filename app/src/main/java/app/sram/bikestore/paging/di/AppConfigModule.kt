package app.sram.bikestore.paging.di

import android.app.Application
import android.content.Context
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
}
