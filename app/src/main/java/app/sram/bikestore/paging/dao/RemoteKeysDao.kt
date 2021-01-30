package app.sram.bikestore.paging.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<MoviesDb.MovieRemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE placeId = :placeId")
    fun remoteKeysByMovieId(placeId: String): MoviesDb.MovieRemoteKeys?

    @Query("DELETE FROM remote_keys")
    fun clearRemoteKeys()
}
