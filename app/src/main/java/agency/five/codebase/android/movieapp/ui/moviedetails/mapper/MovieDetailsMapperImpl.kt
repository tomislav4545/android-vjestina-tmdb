package agency.five.codebase.android.movieapp.ui.moviedetails.mapper

import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {
    override fun toMovieDetailsViewState(movieDetails: MovieDetails): MovieDetailsViewState {

        val crewList = movieDetails.crew.map {
            CrewItemViewState(
                id = it.id,
                name = it.name,
                job = it.job
            )
        }

        val castList = movieDetails.cast.map {
            ActorCardViewState(
                id = it.id,
                name = it.name,
                character = it.character,
                imageUrl = it.imageUrl
            )
        }

        return MovieDetailsViewState(
            id = movieDetails.movie.id,
            imageUrl = movieDetails.movie.imageUrl,
            voteAverage = movieDetails.voteAverage,
            title = movieDetails.movie.title,
            overview = movieDetails.movie.overview,
            isFavorite = movieDetails.movie.isFavorite,
            crew = crewList,
            cast = castList
        )
    }
}
