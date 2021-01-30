package app.sram.bikestore.paging.api

import app.sram.bikestore.paging.data.MoviesResponse
import io.reactivex.Single
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
        @Query("pagetoken") pageToken: String,
        @Query("location") location: String = "47.584420801582866,-122.15182334946458",
        @Query("radius") radius: String = "50000",
        @Query("type") type: String = "bicycle_store"
    ): Single<MoviesResponse>
}
