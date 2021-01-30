package app.sram.bikestore.paging.dao

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.sram.bikestore.paging.data.Image
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MoviesDb(
    val total: Int = 0,
    val page: Int = 0,
    val movieEntities: List<MovieEntity>
) : Parcelable {

    @IgnoredOnParcel
    val endOfPage = total == page

    @Parcelize
    @Entity(tableName = "movieEntities")
    data class MovieEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val movieId: Long,
        val popularity: Double,
        val video: Boolean,
        val poster: Image?,
        val adult: Boolean,
        val backdrop: Image?,
        val originalLanguage: String,
        val originalTitle: String,
        val title: String,
        val voteAverage: Double,
        val overview: String,
        val releaseDate: Date?
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "movie_remote_keys")
    data class MovieRemoteKeys(
        @PrimaryKey val movieId: Long,
        val prevKey: Int?,
        val nextKey: Int?
    ) : Parcelable
}
