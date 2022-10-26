package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import agency.five.codebase.android.movieapp.mock.MoviesMock.getActor
import androidx.compose.ui.layout.ContentScale

// imports
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
    Card(modifier = modifier.width(125.dp).height(200.dp).padding(10.dp), shape= RoundedCornerShape(10.dp),elevation = 10.dp){
        Column() {
            AsyncImage(model = actorCardViewState.imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = modifier.height(110.dp))
            Text(text = actorCardViewState.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Left ,modifier = modifier.padding(top=10.dp, bottom = 5.dp).padding(horizontal = 10.dp))
            Text(text = actorCardViewState.character, fontSize = 9.sp, textAlign = TextAlign.Left, modifier = modifier.padding(start = 10.dp).alpha(ContentAlpha.disabled).width(100.dp))
        }
    }
}
@Preview
@Composable
private fun ActorCardPreview() {
    val actor = getActor()
    ActorCard(actorCardViewState = ActorCardViewState(actor.imageUrl,actor.name,actor.character))
}