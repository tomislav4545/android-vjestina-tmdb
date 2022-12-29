package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
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

    private val popularCategorySelected = MutableStateFlow(popularCategories[0])
    private val nowPlayingCategorySelected = MutableStateFlow(nowPlayingCategories[0])
    private val upcomingCategorySelected = MutableStateFlow(upcomingCategories[0])

    val popularMoviesHomeViewState: StateFlow<HomeMovieCategoryViewState> =
        popularCategorySelected.flatMapLatest { selected ->
            movieRepository.popularMovies(selected).map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = popularCategories,
                    selectedMovieCategory = selected,
                    movies = movies,
                )
            }
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            HomeMovieCategoryViewState(
                emptyList(),
                emptyList()
            )
        )

    val nowPlayingMoviesHomeViewState: StateFlow<HomeMovieCategoryViewState> =
        nowPlayingCategorySelected.flatMapLatest { selected ->
            movieRepository.nowPlayingMovies(selected).map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = nowPlayingCategories,
                    selectedMovieCategory = selected,
                    movies = movies,
                )
            }
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            HomeMovieCategoryViewState(
                emptyList(),
                emptyList()
            )
        )

    val upcomingMoviesHomeViewState: StateFlow<HomeMovieCategoryViewState> =
        upcomingCategorySelected.flatMapLatest { selected ->
            movieRepository.upcomingMovies(selected).map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    movieCategories = upcomingCategories,
                    selectedMovieCategory = selected,
                    movies = movies,
                )
            }
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            HomeMovieCategoryViewState(
                emptyList(),
                emptyList()
            )
        )

    fun changeCategory(id: Int) {
        viewModelScope.launch {
            when (id) {
                MovieCategory.POPULAR_STREAMING.ordinal,
                MovieCategory.POPULAR_ON_TV.ordinal,
                MovieCategory.POPULAR_FOR_RENT.ordinal,
                MovieCategory.POPULAR_IN_THEATRES.ordinal,
                -> {
                    popularCategorySelected.value = MovieCategory.values()[id]
                }

                MovieCategory.NOW_PLAYING_TV.ordinal,
                MovieCategory.NOW_PLAYING_MOVIES.ordinal,
                -> {
                    nowPlayingCategorySelected.value = MovieCategory.values()[id]
                }
                MovieCategory.UPCOMING_TODAY.ordinal,
                MovieCategory.UPCOMING_THIS_WEEK.ordinal,
                -> {
                    upcomingCategorySelected.value = MovieCategory.values()[id]
                }
            }
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(id)
        }
    }
}
