package app.sram.bikestore.paging

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.adrena.commerce.paging3.R
import java.util.*

object Injection {
    private fun provideLocale(): Locale = Locale.getDefault()
    private fun provideDatabase(context: Context): MovieDatabase = MovieDatabase.getInstance(context)


    fun provideRxRemoteViewModel(context: Context): ViewModelProvider.Factory {
        val remoteMediator =
            GetMoviesRxRemoteMediator(
                service = TMDBService.create(),
                database = provideDatabase(context),
                apiKey = context.getString(R.string.api_key),
                mapper = MoviesMapper(),
                locale = provideLocale()
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