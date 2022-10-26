package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview



@Composable
fun FavoriteButton(modifier: Modifier = Modifier){
    var isFavorite by remember{ mutableStateOf(false) }
    Image(
        painter = painterResource(id = if (isFavorite) R.drawable.favoritebuttonfilled else R.drawable.favoritebutton),
        contentDescription = null,
        modifier = Modifier
            .clickable {
                isFavorite = isFavorite.not()
            }
        )
}

@Preview
@Composable
private fun FavoriteButtonPreview() {
    FavoriteButton()
}