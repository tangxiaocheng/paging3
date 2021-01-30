package app.sram.bikestore.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BikeStoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieEntities: List<MoviesDb.MovieEntity>)

    @Query("SELECT * FROM movieEntities ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, MoviesDb.MovieEntity>

    @Query("DELETE FROM movieEntities")
    fun clearAll()
}
