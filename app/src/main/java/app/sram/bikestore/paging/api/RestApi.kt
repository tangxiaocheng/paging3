package app.sram.bikestore.paging.api

import app.sram.bikestore.paging.dao.AuthInterceptor
import app.sram.bikestore.paging.data.MoviesResponse
import com.adrena.commerce.paging3.BuildConfig
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {
//    https://maps.googleapis.com/maps/api/place/nearbysearch/json?
//    key=AIzaSyAiPeUFkMSj7_xfnYQY6Bi2bCiuszuU2e4
//    &location=47.585220,-122.152400
//    &type=bicycle_store
//    &radius=100000
//    &pagetoken=fgdsfgdsrr
    @GET("maps/api/place/nearbysearch/json")
    fun popularMovieRx(
        @Query("page") page: Int,
        @Query("location") location: String = "47.584420801582866,-122.15182334946458",
        @Query("radius") radius: String = "50000",
        @Query("type") type: String = "bicycle_store"
    ): Single<MoviesResponse>

    companion object {

        fun create(): RestApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(AuthInterceptor())
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
}
