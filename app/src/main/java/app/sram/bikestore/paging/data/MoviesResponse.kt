package app.sram.bikestore.paging.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MoviesResponse(
    val totalPages: Int = 100,
    val page: Int = 0,
    val results: List<MovieModel>,
    val status: String,
    val errorMessage: String?
) {

    data class MovieModel(
        val rating: Double,
        val userRatingsTotal: Int,
        val placeId: String,
        val vicinity: String,
        val title: String,
        val photos: List<Photo>
    )
}

@Parcelize
data class Photo(
    val photoReference: String,
    val width: Int,
    val height: Int
) : Parcelable
