package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val MovieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()
val movieDetailsViewState = MovieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute(
    viewModel: MovieDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val movieDetailsViewState: MovieDetailsViewState by viewModel.movieDetailsViewState.collectAsState()

    MovieDetailsScreen(
        movieDetailsViewState = movieDetailsViewState,
        onFavoriteButtonClicked = {
            viewModel.toggleFavorite(movieDetailsViewState.id)
        },
        modifier = modifier
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
    ) {
        item {
            MovieImage(movieDetailsViewState, onFavoriteButtonClicked)
        }

        item {
            MovieOverview(movieDetailsViewState)
        }

        item {
            MovieCrewman(movieDetailsViewState)
        }

        item {
            MovieCast(movieDetailsViewState)
        }
    }
}

@Composable
fun MovieCast(
    movieDetailsViewState: MovieDetailsViewState,
) {
    Column {
        Text(
            text = stringResource(id = R.string.top_billed_cast),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small
                )
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
        ) {
            items(movieDetailsViewState.cast.size) { actor ->
                ActorCard(
                    actorCardViewState = ActorCardViewState(
                        id = movieDetailsViewState.cast[actor].id,
                        imageUrl = movieDetailsViewState.cast[actor].imageUrl,
                        name = movieDetailsViewState.cast[actor].name,
                        character = movieDetailsViewState.cast[actor].character
                    ),
                    modifier = Modifier
                        .size(
                            width = dimensionResource(id = R.dimen.actor_card_width),
                            height = dimensionResource(id = R.dimen.actor_card_height)
                        )
                )
            }
        }
    }
}

@Composable
fun MovieCrewman(
    movieDetailsViewState: MovieDetailsViewState
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.crew_horizontal_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.crew_vertical_spacing)),
        userScrollEnabled = false,
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.crewman_height))
    ) {
        items(
            movieDetailsViewState.crew,
        ) { crewman ->
            CrewItem(
                crewItemViewState = CrewItemViewState(
                    id = crewman.id,
                    name = crewman.name,
                    job = crewman.job
                ),
                modifier = Modifier
                    .padding(
                        vertical = MaterialTheme.spacing.small,
                        horizontal = MaterialTheme.spacing.small
                    )
            )
        }
    }

}

@Composable
fun MovieOverview(
    movieDetailsViewState: MovieDetailsViewState
) {
    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        Text(
            text = stringResource(id = R.string.overview),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(vertical = MaterialTheme.spacing.medium)
        )
        Text(
            text = movieDetailsViewState.overview,
            color = MaterialTheme.colors.onSurface,
            fontSize = 15.sp,
        )
    }
}

@Composable
fun MovieImage(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteClicked: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = movieDetailsViewState.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.movie_image_height))
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UserScoreProgressBar(
                    score = movieDetailsViewState.voteAverage,
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.extraSmall)
                        .size(dimensionResource(id = R.dimen.userscore_size))
                )
                Text(
                    text = stringResource(id = R.string.user_score),
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onPrimary,
                )
            }
            Text(
                text = movieDetailsViewState.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.small)
            )
            FavoriteButton(
                isFavorite = movieDetailsViewState.isFavorite,
                onClick = onFavoriteClicked,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.favorite_button_size))
                    .padding(MaterialTheme.spacing.small)
            )
        }
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieAppTheme {
        MovieDetailsScreen(
            movieDetailsViewState = movieDetailsViewState,
            onFavoriteButtonClicked = {}
        )
    }
}
