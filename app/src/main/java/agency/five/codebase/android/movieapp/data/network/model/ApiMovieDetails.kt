package agency.five.codebase.android.movieapp.data.network.model

import BASE_IMAGE_URL
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("genres")
    val genreIds: List<Genre>,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("runtime")
    val runtime: Int,
) {
    fun fetchMovieDetails(isFavorite: Boolean, crew: List<ApiCrew>, cast: List<ApiCast>) =
        MovieDetails(
            movie = Movie(
                id = id,
                title = title,
                overview = overview,
                imageUrl = "$BASE_IMAGE_URL/$posterPath",
                isFavorite = isFavorite
            ),
            voteAverage = voteAverage.toFloat(),
            releaseDate = releaseDate ?: "",
            language = originalLanguage,
            runtime = runtime,
            crew = crew.map {
                it.toCrewman()
            },
            cast = cast.map {
                it.toActor()
            }
        )
}

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int
)
