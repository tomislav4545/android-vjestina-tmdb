package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val homeScreenMapper: HomeScreenMapper,
) : ViewModel() {

    private val popularCategories = listOf(
        MovieCategory.POPULAR_STREAMING,
        MovieCategory.POPULAR_ON_TV,
        MovieCategory.POPULAR_FOR_RENT,
        MovieCategory.POPULAR_IN_THEATRES
    )

    private val nowPlayingCategories = listOf(
        MovieCategory.NOW_PLAYING_MOVIES,
        MovieCategory.NOW_PLAYING_TV
    )

    private val upcomingCategories = listOf(
        MovieCategory.UPCOMING_TODAY,
        MovieCategory.UPCOMING_THIS_WEEK
    )

    private val popularSelectedCategory: MutableState<MovieCategory> =
        mutableStateOf(MovieCategory.POPULAR_STREAMING)
    private val upcomingSelectedCategory: MutableState<MovieCategory> =
        mutableStateOf(MovieCategory.UPCOMING_TODAY)
    private val nowPlayingSelectedCategory: MutableState<MovieCategory> =
        mutableStateOf(MovieCategory.NOW_PLAYING_TV)

    private val _popularMoviesHomeViewState = MutableStateFlow(
        HomeMovieCategoryViewState(
            emptyList(),
            emptyList()
        )
    )
    val popularMoviesHomeViewState: StateFlow<HomeMovieCategoryViewState> =
        _popularMoviesHomeViewState.asStateFlow()

    private val _nowPlayingMoviesHomeViewState = MutableStateFlow(
        HomeMovieCategoryViewState(
            emptyList(), emptyList()
        )
    )
    val nowPlayingMoviesHomeViewState: StateFlow<HomeMovieCategoryViewState> =
        _nowPlayingMoviesHomeViewState.asStateFlow()

    private val _upcomingMoviesHomeViewState = MutableStateFlow(
        HomeMovieCategoryViewState(
            emptyList(),
            emptyList()
        )
    )
    val upcomingMoviesHomeViewState: StateFlow<HomeMovieCategoryViewState> =
        _upcomingMoviesHomeViewState.asStateFlow()

    init {
        getPopularMovies()
        getUpcomingMovies()
        getNowPlayingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING).collect {
                _popularMoviesHomeViewState.value = homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = popularCategories,
                    selectedMovieCategory = popularSelectedCategory.value,
                    movies = it
                )
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            movieRepository.upcomingMovies(MovieCategory.UPCOMING_TODAY).collect {
                _upcomingMoviesHomeViewState.value = homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = upcomingCategories,
                    selectedMovieCategory = upcomingSelectedCategory.value,
                    movies = it
                )
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            movieRepository.nowPlayingMovies(MovieCategory.NOW_PLAYING_MOVIES).collect {
                _nowPlayingMoviesHomeViewState.value =
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        movieCategories = nowPlayingCategories,
                        selectedMovieCategory = nowPlayingSelectedCategory.value,
                        movies = it
                    )
            }
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(id)
        }
    }

    fun changeCategory(categoryId: Int) {
        when (categoryId) {
            MovieCategory.POPULAR_STREAMING.ordinal,
            MovieCategory.POPULAR_FOR_RENT.ordinal,
            MovieCategory.POPULAR_ON_TV.ordinal,
            MovieCategory.POPULAR_IN_THEATRES.ordinal
            -> {
                popularSelectedCategory.value = MovieCategory.values()[categoryId]
                getPopularMovies()

            }

            MovieCategory.NOW_PLAYING_MOVIES.ordinal,
            MovieCategory.NOW_PLAYING_TV.ordinal
            -> {
                nowPlayingSelectedCategory.value = MovieCategory.values()[categoryId]
                getNowPlayingMovies()
            }

            MovieCategory.UPCOMING_TODAY.ordinal,
            MovieCategory.UPCOMING_THIS_WEEK.ordinal
            -> {
                upcomingSelectedCategory.value = MovieCategory.values()[categoryId]
                getUpcomingMovies()
            }

            else -> throw IllegalStateException()
        }
    }
}
