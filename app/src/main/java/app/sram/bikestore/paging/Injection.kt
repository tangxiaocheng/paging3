package app.sram.bikestore.paging

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import java.util.*

object Injection {
    private fun provideLocale(): Locale = Locale.getDefault()
    private fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)


    fun provideRxRemoteViewModel(context: Context): ViewModelProvider.Factory {
        val remoteMediator =
            GetMoviesRxRemoteMediator(
                service = RestApi.create(),
                database = provideDatabase(context),
                mapper = MoviesMapper()
            )

        val repository =
            GetMoviesRxRemoteRepositoryImpl(
                database = provideDatabase(context),
                remoteMediator = remoteMediator
            )

        return GetMoviesRxViewModelFactory(
            repository
        )
    }
}