package app.sram.bikestore.paging.data

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("total_pages") val totalPages: Int = 0,
    val page: Int = 0,
    val results: List<MovieModel>
) {

    data class MovieModel(
        @SerializedName("rating")
        val popularity: Double,
        @SerializedName("vote_count") val voteCount: Int,
        val video: Boolean,
        @SerializedName("poster_path") val posterPath: String?,
        val id: Long,
        val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        val title: String,
        @SerializedName("vote_average") val voteAverage: Double,
        val overview: String,
        @SerializedName("release_date") val releaseDate: String?
    )
}
