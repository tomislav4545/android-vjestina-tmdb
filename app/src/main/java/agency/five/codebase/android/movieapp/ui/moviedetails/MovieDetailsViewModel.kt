package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
    private val movieDetailsScreenMapper: MovieDetailsMapper,
) : ViewModel() {
    val movieDetailViewState: StateFlow<MovieDetailsViewState> =
        movieRepository.movieDetails(movieId).map { movieDetails ->
            movieDetailsScreenMapper.toMovieDetailsViewState(movieDetails)
        }.stateIn(viewModelScope, SharingStarted.Lazily, MovieDetailsViewState.getEmptyObject())

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(id)
        }
    }
}
