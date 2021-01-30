package app.sram.bikestore.paging.di

import android.app.Application
import okhttp3.HttpUrl

class AppConfig(
    val serverUrl: HttpUrl,
    val application: Application
)
