package app.sram.bikestore.paging.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.sram.bikestore.paging.data.Converters

@Database(
    entities = [MoviesDb.MovieEntity::class, MoviesDb.MovieRemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesRxDao(): BikeStoresDao
    abstract fun movieRemoteKeysRxDao(): RemoteKeysDao
}
