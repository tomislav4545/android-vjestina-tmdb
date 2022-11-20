package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class MovieCardViewState(
    val imageUrl: String?,
    val id: Int,
    val isFavorite: Boolean,
    val title: String
)

@Composable
fun MovieCard(
    modifier: Modifier,
    movieCardViewState: MovieCardViewState,
    onFavoriteButtonClicked: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius)),
        elevation = dimensionResource(id = R.dimen.elevation)
    ) {
        Box {
            AsyncImage(
                model = movieCardViewState.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            FavoriteButton(
                isFavorite = movieCardViewState.isFavorite,
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.small,
                        top = MaterialTheme.spacing.small
                    ),
                onClick = { onFavoriteButtonClicked() }
            )
        }
    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    val movie = MoviesMock.getMoviesList()[4]
    val movieCardViewState =
        MovieCardViewState(imageUrl = movie.imageUrl, id = movie.id, isFavorite = movie.isFavorite, title = movie.title)
    MovieCard(
        movieCardViewState = movieCardViewState,
        modifier = Modifier
            .width(125.dp)
            .height(205.dp)
            .padding(10.dp),
        onClick = { },
        onFavoriteButtonClicked = { }
    )
}
