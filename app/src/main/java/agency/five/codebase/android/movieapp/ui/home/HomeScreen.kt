package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

val popularCategoryLabels = listOf(
    MovieCategory.POPULAR_STREAMING,
    MovieCategory.POPULAR_ON_TV,
    MovieCategory.POPULAR_FOR_RENT,
    MovieCategory.POPULAR_IN_THEATRES
)

val nowPlayingCategoryLabels = listOf(
    MovieCategory.NOW_PLAYING_MOVIES,
    MovieCategory.NOW_PLAYING_TV
)

val upcomingCategoryLabels = listOf(
    MovieCategory.UPCOMING_TODAY,
    MovieCategory.UPCOMING_THIS_WEEK
)

val popularCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    popularCategoryLabels,
    MovieCategory.POPULAR_STREAMING,
    MoviesMock.getMoviesList()
)
val nowPlayingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    nowPlayingCategoryLabels,
    MovieCategory.NOW_PLAYING_MOVIES,
    MoviesMock.getMoviesList()
)
val upcomingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    upcomingCategoryLabels,
    MovieCategory.UPCOMING_TODAY,
    MoviesMock.getMoviesList()
)

@Composable
fun HomeScreenRoute(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    var popularViewState by remember { mutableStateOf(popularCategoryViewState) }
    var nowPlayingViewState by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var upcomingViewState by remember { mutableStateOf(upcomingCategoryViewState) }

    HomeScreen(
        popularViewState = popularViewState,
        nowPlayingViewState = nowPlayingViewState,
        upcomingViewState = upcomingViewState,
        onPopularLabelClick = {
            popularViewState = homeScreenMapper.toHomeMovieCategoryViewState(
                popularCategoryLabels,
                MovieCategory.getByOrdinal(it)!!,
                MoviesMock.getMoviesList()
            )
        },
        onNowPlayingLabelClick = {
            nowPlayingViewState = homeScreenMapper.toHomeMovieCategoryViewState(
                nowPlayingCategoryLabels,
                MovieCategory.getByOrdinal(it)!!,
                MoviesMock.getMoviesList()
            )
        },
        onUpcomingLabelClick = {
            upcomingViewState = homeScreenMapper.toHomeMovieCategoryViewState(
                upcomingCategoryLabels,
                MovieCategory.getByOrdinal(it)!!,
                MoviesMock.getMoviesList()
            )
        },
        onNavigateToMovieDetails = onNavigateToMovieDetails
    )
}

@Composable
fun HomeScreen(
    popularViewState: HomeMovieCategoryViewState,
    nowPlayingViewState: HomeMovieCategoryViewState,
    upcomingViewState: HomeMovieCategoryViewState,
    onPopularLabelClick: (Int) -> Unit,
    onNowPlayingLabelClick: (Int) -> Unit,
    onUpcomingLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.home_screen_spacing))
    ) {
        item {
            PopularMovies(popularViewState, onPopularLabelClick, onNavigateToMovieDetails)
        }

        item {
            NowPlayingMovies(nowPlayingViewState, onNowPlayingLabelClick, onNavigateToMovieDetails)
        }

        item {
            UpcomingMovies(upcomingViewState, onUpcomingLabelClick, onNavigateToMovieDetails)
        }
    }
}

@Composable
fun UpcomingMovies(
    upcomingViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit
) {
    Text(
        text = stringResource(id = R.string.upcoming),
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.medium_spacing))
    ) {
        items(upcomingViewState.movieCategories.size) { category ->
            MovieCategoryLabel(
                movieCategoryLabelViewState = MovieCategoryLabelViewState(
                    upcomingViewState.movieCategories[category].itemId,
                    upcomingViewState.movieCategories[category].isSelected,
                    upcomingViewState.movieCategories[category].categoryText
                ),
                onClick = { onLabelClick(upcomingViewState.movieCategories[category].itemId) },
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.medium_spacing))
    ) {
        items(upcomingViewState.movies.size) { movie ->
            MovieCard(
                movieCardViewState = MovieCardViewState(
                    imageUrl = upcomingViewState.movies[movie].imageUrl,
                    id = upcomingViewState.movies[movie].movieId,
                    isFavorite = upcomingViewState.movies[movie].isFavorite,
                    title = upcomingViewState.movies[movie].title
                ),
                onClick = { onNavigateToMovieDetails(upcomingViewState.movies[movie].movieId) },
                onFavoriteButtonClicked = {},
                modifier = Modifier
            )
        }
    }
}

@Composable
fun NowPlayingMovies(
    nowPlayingViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit
) {
    Text(
        text = stringResource(id = R.string.now_playing),
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.medium_spacing))
    ) {
        items(nowPlayingViewState.movieCategories.size) { category ->
            MovieCategoryLabel(
                movieCategoryLabelViewState = MovieCategoryLabelViewState(
                    nowPlayingViewState.movieCategories[category].itemId,
                    nowPlayingViewState.movieCategories[category].isSelected,
                    nowPlayingViewState.movieCategories[category].categoryText
                ),
                onClick = { onLabelClick(nowPlayingViewState.movieCategories[category].itemId) }
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.medium_spacing))
    ) {
        items(nowPlayingViewState.movies.size) { movie ->
            MovieCard(
                movieCardViewState = MovieCardViewState(
                    imageUrl = nowPlayingViewState.movies[movie].imageUrl,
                    id = nowPlayingViewState.movies[movie].movieId,
                    isFavorite = nowPlayingViewState.movies[movie].isFavorite,
                    title = nowPlayingViewState.movies[movie].title
                ),
                onClick = { onNavigateToMovieDetails(nowPlayingViewState.movies[movie].movieId) },
                onFavoriteButtonClicked = {},
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PopularMovies(
    popularViewState: HomeMovieCategoryViewState,
    onLabelClick: (Int) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit
) {
    Text(
        text = stringResource(id = R.string.whats_popular),
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.medium_spacing))
    ) {
        items(popularViewState.movieCategories.size) { category ->
            MovieCategoryLabel(
                movieCategoryLabelViewState = MovieCategoryLabelViewState(
                    popularViewState.movieCategories[category].itemId,
                    popularViewState.movieCategories[category].isSelected,
                    popularViewState.movieCategories[category].categoryText
                ),
                onClick = { onLabelClick(popularViewState.movieCategories[category].itemId) }
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.medium_spacing))
    ) {
        items(popularViewState.movies.size) { movie ->
            MovieCard(
                movieCardViewState = MovieCardViewState(
                    imageUrl = popularViewState.movies[movie].imageUrl,
                    id = popularViewState.movies[movie].movieId,
                    isFavorite = popularViewState.movies[movie].isFavorite,
                    title = popularViewState.movies[movie].title
                ),
                onClick = { onNavigateToMovieDetails(popularViewState.movies[movie].movieId) },
                onFavoriteButtonClicked = {},
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

    HomeScreenRoute {

    }
}
