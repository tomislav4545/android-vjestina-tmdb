package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
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
import agency.five.codebase.android.movieapp.ui.theme.Spacing
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow

data class ActorCardViewState(
    val id: Int,
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
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius)),
        elevation = dimensionResource(id = R.dimen.elevation)
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
                    .padding(
                        top = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.extraSmall
                    )
                    .padding(horizontal = MaterialTheme.spacing.small),
                style = Typography.h3,
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = actorCardViewState.character,
                modifier = Modifier.padding(start = MaterialTheme.spacing.small, end = MaterialTheme.spacing.extraSmall, bottom = MaterialTheme.spacing.extraSmall),
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
        actorCardViewState = ActorCardViewState(actor.id,actor.imageUrl, actor.name, actor.character),
        modifier = Modifier
            .padding(10.dp)
            .size(
                width = 130.dp,
                height = 220.dp,
            )
    )
}
