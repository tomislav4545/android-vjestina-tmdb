package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.component.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel,
    onNavigateToMovieDetails: (Int) -> Unit
) {
    val upcomingMoviesViewState: HomeMovieCategoryViewState by viewModel.upcomingMoviesHomeViewState.collectAsState()
    val nowPlayingMoviesViewState: HomeMovieCategoryViewState by viewModel.nowPlayingMoviesHomeViewState.collectAsState()
    val popularMoviesViewState: HomeMovieCategoryViewState by viewModel.popularMoviesHomeViewState.collectAsState()

    HomeScreen(
        popularCategoryViewState = popularMoviesViewState,
        nowPlayingCategoryViewState = nowPlayingMoviesViewState,
        upcomingCategoryViewState = upcomingMoviesViewState,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onFavoriteButtonClicked = { viewModel.toggleFavorite(it) } ,
        onMovieCategoryClicked = { categoryId -> viewModel.changeCategory(categoryId) }
    )
}

@Composable
private fun HomeScreen(
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
    onMovieCategoryClicked: (Int) -> Unit,
    popularCategoryViewState: HomeMovieCategoryViewState,
    nowPlayingCategoryViewState: HomeMovieCategoryViewState,
    upcomingCategoryViewState: HomeMovieCategoryViewState
) {
    Column(
        modifier = Modifier
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true
            )
    )
    {
        MovieCategoryLayout(
            modifier = Modifier,
            categoryViewState = popularCategoryViewState,
            title = R.string.whats_popular,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteButtonClicked = onFavoriteButtonClicked,
            onCategoryClicked = onMovieCategoryClicked

        )

        MovieCategoryLayout(
            modifier = Modifier,
            categoryViewState = nowPlayingCategoryViewState,
            title = R.string.now_playing,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteButtonClicked = onFavoriteButtonClicked,
            onCategoryClicked = onMovieCategoryClicked
        )

        MovieCategoryLayout(
            modifier = Modifier,
            categoryViewState = upcomingCategoryViewState,
            title = R.string.upcoming,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteButtonClicked = onFavoriteButtonClicked,
            onCategoryClicked = onMovieCategoryClicked
        )
    }
}

@Composable
private fun MovieCategoryLayout(
    modifier: Modifier,
    categoryViewState: HomeMovieCategoryViewState,
    title: Int,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
    onCategoryClicked: (Int) -> Unit
) {
    Text(
        text = stringResource(id = title),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.home_screen_spacing))
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_spacing)),
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.home_screen_spacing),
                end = dimensionResource(id = R.dimen.home_screen_spacing),
                bottom = dimensionResource(id = R.dimen.home_screen_spacing)
            )
    ) {
        items(
            items = categoryViewState.movieCategories,
            key = { category -> category.itemId }) { category ->
            MovieCategoryLabel(
                movieCategoryLabelViewState = category,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.small_spacing)),
                onClick = { onCategoryClicked(category.itemId) }
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_spacing)),
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.home_screen_spacing),
                end = dimensionResource(id = R.dimen.home_screen_spacing),
                bottom = dimensionResource(id = R.dimen.home_screen_spacing)
            ),
        content = {
            items(
                items = categoryViewState.movies,
                key = { movie -> movie.movieId }) { movie ->
                MovieCard(
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.movie_card_favorites_width))
                        .height(dimensionResource(id = R.dimen.movie_card_favorites_height)),
                    movieCardViewState = MovieCardViewState(
                        imageUrl = movie.movie.imageUrl,
                        isFavorite = movie.movie.isFavorite
                    ),
                    onFavoriteButtonClicked = { onFavoriteButtonClicked(movie.movieId) },
                    onClick = { onNavigateToMovieDetails(movie.movieId) }
                )
            }
        }
    )
}
