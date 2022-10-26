package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.Movie
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .width(125.dp)
            .height(205.dp)
            .padding(10.dp).clickable{}, shape = RoundedCornerShape(10.dp), elevation = 10.dp
    ) {
        Box {
            AsyncImage(model = movie.imageUrl, contentDescription = null, contentScale = ContentScale.Crop)
            FavoriteButton()
        }
    }
}
@Preview
@Composable
private fun MovieCardPreview() {
    val movie = MoviesMock.getMoviesList()[4]
    MovieCard(movie = movie)
}