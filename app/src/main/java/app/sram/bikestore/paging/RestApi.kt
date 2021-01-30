package app.sram.bikestore.paging

import app.sram.bikestore.paging.dao.AuthInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("movie/popular")
    fun popularMovieRx(
        @Query("page") page: Int,
        @Query("language") language: String
    ) : Single<MoviesResponse>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): RestApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(AuthInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RestApi::class.java)
        }
    }
}