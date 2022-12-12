package agency.five.codebase.android.movieapp.ui.favorites.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesMovieViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState
import agency.five.codebase.android.movieapp.ui.favorites.favoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoriteMovies: List<Movie>): FavoritesViewState {

        val favoriteMoviesViewStates = favoriteMovies.map {
            mapMovie(it)
        }

        return FavoritesViewState(favoriteMoviesViewStates)
    }

    private fun mapMovie(movie: Movie): FavoritesMovieViewState {
        return FavoritesMovieViewState(
            id = movie.id,
            movieCardViewState = MovieCardViewState(
                imageUrl = movie.imageUrl,
                isFavorite = movie.isFavorite
            )
        )
    }

}
