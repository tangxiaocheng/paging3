package app.sram.bikestore.paging.dao

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.sram.bikestore.paging.data.Image
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

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
        val placeId: String,
        val popularity: Double,
        val poster: Image?,
        val title: String
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "remote_keys")
    data class MovieRemoteKeys(
        @PrimaryKey val placeId: String,
        val prevKey: Int?,
        val nextKey: Int?
    ) : Parcelable
}
