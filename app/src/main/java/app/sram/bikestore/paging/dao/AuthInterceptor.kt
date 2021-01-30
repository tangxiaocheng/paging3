package app.sram.bikestore.paging.dao

import app.sram.bikestore.paging.di.AppScope
import com.adrena.commerce.paging3.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject

@AppScope
class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val url = chain.request().url.newBuilder()
            .addQueryParameter(BuildConfig.KEY_STRING, BuildConfig.MOVIE_KEY)
            .addQueryParameter("language", Locale.getDefault().language)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
