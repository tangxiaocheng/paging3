package app.sram.bikestore.paging.data

import app.sram.bikestore.paging.dao.MoviesDb
import app.sram.bikestore.paging.di.AppScope
import javax.inject.Inject

@AppScope
class MoviesMapper @Inject constructor() {

    fun transform(response: MoviesResponse): MoviesDb {
        return with(response) {
            MoviesDb(
                total = totalPages,
                page = page,
                movieEntities = results.map {
                    MoviesDb.MovieEntity(
                        0,
                        it.placeId,
                        it.rating,
                        it.photos.first().let { photo -> Image(photo.photoReference) },
                        it.title
                    )
                }
            )
        }
    }
}
