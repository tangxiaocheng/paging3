package app.sram.bikestore.paging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.sram.bikestore.paging.dao.BikeStoresDao
import app.sram.bikestore.paging.dao.RemoteKeysDao

@Database(
    entities = [Movies.Movie::class, Movies.MovieRemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun moviesRxDao(): BikeStoresDao
    abstract fun movieRemoteKeysRxDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "TMDB.db")
                .build()
    }
}