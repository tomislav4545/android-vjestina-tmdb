package agency.five.codebase.android.movieapp.data.repository

import agency.five.codebase.android.movieapp.data.database.DbFavoriteMovie
import agency.five.codebase.android.movieapp.data.database.FavoriteMovieDao
import agency.five.codebase.android.movieapp.data.network.MovieService
import agency.five.codebase.android.movieapp.data.network.model.MovieResponse
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: FavoriteMovieDao,
    private val bgDispatcher: CoroutineDispatcher,
) : MovieRepository {

    private val moviesByCategory: Map<MovieCategory, Flow<List<Movie>>> = MovieCategory.values()
        .associateWith { movieCategory ->
            flow {
                val movieResponse: MovieResponse = when (movieCategory) {
                    MovieCategory.POPULAR_STREAMING -> movieService.fetchPopularMovies()

                    MovieCategory.POPULAR_ON_TV -> movieService.fetchUpcomingMovies()

                    MovieCategory.POPULAR_FOR_RENT -> movieService.fetchTopRatedMovies()

                    MovieCategory.POPULAR_IN_THEATRES -> movieService.fetchNowPlayingMovies()

                    MovieCategory.NOW_PLAYING_MOVIES -> movieService.fetchNowPlayingMovies()

                    MovieCategory.NOW_PLAYING_TV -> movieService.fetchTopRatedMovies()

                    MovieCategory.UPCOMING_TODAY -> movieService.fetchNowPlayingMovies()

                    MovieCategory.UPCOMING_THIS_WEEK -> movieService.fetchTopRatedMovies()
                }
                emit(movieResponse.movies)

            }.flatMapLatest { apiMovies ->
                movieDao.favorites().map { favoriteMovies ->
                    apiMovies.map { apiMovie ->
                        apiMovie.toMovie(isFavorite = favoriteMovies.any { it.id == apiMovie.id })
                    }
                }
            }.shareIn(
                scope = CoroutineScope(bgDispatcher),
                started = SharingStarted.WhileSubscribed(1000L),
                replay = 1,
            )
        }

    private val favorites = movieDao.favorites().mapLatest { dbFavoriteMovies ->
        dbFavoriteMovies.map { dbFavoriteMovie ->
            Movie(
                id = dbFavoriteMovie.id,
                imageUrl = dbFavoriteMovie.posterUrl,
                title = "",
                overview = "",
                isFavorite = true,
            )
        }
    }.flowOn(bgDispatcher)

    override fun movies(movieCategory: MovieCategory): Flow<List<Movie>> =
        moviesByCategory[movieCategory]!!

    override fun movieDetails(movieId: Int): Flow<MovieDetails> = flow {
        emit(movieService.fetchMovieDetails(movieId) to movieService.fetchMovieCredits(movieId))
    }.flatMapLatest { (apiMovieDetails, apiMovieCredits) ->
        movieDao.favorites()
            .map { favoriteMovies ->
                apiMovieDetails.fetchMovieDetails(
                    isFavorite = favoriteMovies.any { it.id == apiMovieDetails.id },
                    crew = apiMovieCredits.crew,
                    cast = apiMovieCredits.cast,
                )
            }
    }.flowOn(bgDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = favorites


    private suspend fun findMovie(movieId: Int): Movie? {
        for (movieCategory in MovieCategory.values()) {
            val movies = movies(movieCategory).first()
            for (movie in movies) {
                if (movie.id == movieId) {
                    return movie
                }
            }
        }
        return null
    }

    override suspend fun addMovieToFavorites(movieId: Int) {
        val movie = findMovie(movieId)
        val dbFavoriteMovie = DbFavoriteMovie(id = movieId, posterUrl = movie?.imageUrl ?: "")
        runBlocking(bgDispatcher) {
            movieDao.insertMovie(dbFavoriteMovie)
        }
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        runBlocking(bgDispatcher) {
            movieDao.deleteMovie(movieId)
        }
    }

    override suspend fun toggleFavorite(movieId: Int) {
        val listOfFavorites = movieDao.favorites().first()
        val isFavorite = listOfFavorites.any { it.id == movieId }
        if (isFavorite) removeMovieFromFavorites(movieId)
        else addMovieToFavorites(movieId)
    }

}
