package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import agency.five.codebase.android.movieapp.mock.MoviesMock.getActor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.compose.ui.text.style.TextOverflow

data class ActorCardViewState(
    val imageUrl: String?,
    val name: String,
    val character: String,
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        Column() {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = null,
                modifier = Modifier.weight(0.6f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = actorCardViewState.name,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 5.dp)
                    .padding(horizontal = 10.dp),
                style = Typography.h3,
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = actorCardViewState.character,
                modifier = Modifier.padding(start = 10.dp, end = 5.dp, bottom = 5.dp),
                style = Typography.h4,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun ActorCardPreview() {
    val actor = getActor()
    ActorCard(
        actorCardViewState = ActorCardViewState(actor.imageUrl, actor.name, actor.character),
        modifier = Modifier
            .padding(10.dp)
            .size(
                width = 130.dp,
                height = 220.dp,
            )
    )
}
