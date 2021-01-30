package app.sram.bikestore.paging.di

import android.content.Context
import androidx.room.Room
import app.sram.bikestore.paging.dao.AppDatabase
import app.sram.bikestore.paging.dao.BikeStoresDao
import app.sram.bikestore.paging.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @AppScope
    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        )
//            .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
            .build()
    }

    @AppScope
    @Provides
    fun moviesRxDao(db: AppDatabase): BikeStoresDao {
        return db.moviesRxDao()
    }

    @AppScope
    @Provides
    fun movieRemoteKeysRxDao(db: AppDatabase): RemoteKeysDao {
        return db.movieRemoteKeysRxDao()
    }
}
