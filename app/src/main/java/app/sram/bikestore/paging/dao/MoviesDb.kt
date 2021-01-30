package app.sram.bikestore.paging.dao

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.sram.bikestore.paging.data.Image
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesDb(
    val pageToken: String?,
    val movieEntities: List<MovieEntity>,
    val status: String?
) : Parcelable {

    @IgnoredOnParcel
    val endOfPage = status == "ZERO_RESULTS"

    @Parcelize
    @Entity(tableName = "movieEntities")
    data class MovieEntity(
        @PrimaryKey(autoGenerate = false)
        val placeId: String,
        val popularity: Double,
        val poster: Image?,
        val title: String
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "remote_keys")
    data class MovieRemoteKeys(
        @PrimaryKey val placeId: String,
        val prevPageToken: String?,
        val nextPageToken: String?
    ) : Parcelable
}
