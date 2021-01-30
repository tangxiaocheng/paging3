package app.sram.bikestore.paging.dao

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url.newBuilder()
            .addQueryParameter("api_key", "21440930b1350cd8b4d28accce3a4799")
            .addQueryParameter("language", Locale.getDefault().language)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
