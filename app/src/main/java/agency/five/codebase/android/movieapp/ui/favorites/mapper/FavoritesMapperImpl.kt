package agency.five.codebase.android.movieapp.ui.favorites.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesMovieViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoriteMovies: List<Movie>): FavoritesViewState {

        val favoriteMoviesViewStates = mutableListOf<FavoritesMovieViewState>()

        for (movie in favoriteMovies) {
            favoriteMoviesViewStates.add(mapMovie(movie))
        }

        return FavoritesViewState(
            favoriteMoviesViewStates = favoriteMoviesViewStates
        )
    }

    private fun mapMovie(movie: Movie): FavoritesMovieViewState {
        return FavoritesMovieViewState(
            id = movie.id,
            movieCardViewState = MovieCardViewState(
                imageUrl = movie.imageUrl,
                title = movie.title,
                isFavorite = movie.isFavorite,
                id = movie.id
            )
        )
    }

}
