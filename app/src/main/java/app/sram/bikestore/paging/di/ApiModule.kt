package app.sram.bikestore.paging.di

import android.content.Context
import androidx.room.Room
import app.sram.bikestore.paging.api.RestApi
import app.sram.bikestore.paging.dao.AppDatabase
import app.sram.bikestore.paging.dao.AuthInterceptor
import com.adrena.commerce.paging3.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @AppScope
    @Provides
    fun provideRestApi(authInterceptor: AuthInterceptor): RestApi {

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RestApi::class.java)
    }

}