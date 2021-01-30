package app.sram.bikestore.paging.data

import android.util.Log
import app.sram.bikestore.paging.dao.MoviesDb
import app.sram.bikestore.paging.di.AppScope
import javax.inject.Inject

@AppScope
class MoviesMapper @Inject constructor() {

    fun transform(response: MoviesResponse): MoviesDb {
        return with(response) {
            Log.d("transform", "transform: $response")
            MoviesDb(
                pageToken = nextPageToken,
                movieEntities = results.map {
                    MoviesDb.MovieEntity(
                        it.placeId,
                        it.rating,
                        it.photos.first().let { photo -> Image(photo.photoReference) },
                        it.businessStatus
                    )
                },
                status = status
            )
        }
    }
}
