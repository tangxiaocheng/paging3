package app.sram.bikestore.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BikeStoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movies.Movie>)

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Movies.Movie>

    @Query("DELETE FROM movies")
    fun clearAll()

}