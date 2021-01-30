package app.sram.bikestore.paging

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

class MoviesMapper {

    fun transform(response: MoviesResponse, locale: Locale): Movies {
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
                        it.releaseDate?.let { date ->
                            if (date.isNotEmpty()) {
                                SimpleDateFormat("YYYY-mm-dd", locale).parse(date)
                            } else {
                                null
                            }
                        }
                    )
                }
            )
        }
    }
}