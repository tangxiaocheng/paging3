package app.sram.bikestore.paging.data

import app.sram.bikestore.paging.dao.Movies

class MoviesMapper {

    fun transform(response: MoviesResponse): Movies {
        return with(response) {
            Movies(
                total = total,
                page = page,
                movies = results.map {
                    Movies.Movie(
                        0,
                        it.id,
                        it.popularity,
                        it.video,
                        it.posterPath?.let { path -> Image(path) },
                        it.adult,
                        it.backdropPath?.let { path -> Image(path) },
                        it.originalLanguage,
                        it.originalTitle,
                        it.title,
                        it.voteAverage,
                        it.overview,
                        null
                    )
                }
            )
        }
    }
}