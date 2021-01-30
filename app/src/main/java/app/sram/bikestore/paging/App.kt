package app.sram.bikestore.paging

import android.app.Application
import app.sram.bikestore.paging.di.AppComponent
import app.sram.bikestore.paging.di.AppConfig
import app.sram.bikestore.paging.di.DaggerAppComponent
import com.adrena.commerce.paging3.BuildConfig
import okhttp3.HttpUrl.Companion.toHttpUrl

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appConfig(appConfig())
            .build()
    }

    private fun appConfig() = AppConfig(BuildConfig.SERVER_URL.toHttpUrl(), this)
}
