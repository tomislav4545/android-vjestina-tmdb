package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getCrewman
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class CrewItemViewState(
    val id: Int,
    val name: String,
    val job: String
)

@Composable
fun CrewItem(
    crewItemViewState: CrewItemViewState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = crewItemViewState.name,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
            textAlign = TextAlign.Left,
            style = Typography.h3,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = crewItemViewState.job,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
            style = Typography.h3,
            fontWeight = FontWeight.Light,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CrewmanDetailsPreview() {
    val crewman = getCrewman()
    val crewItemViewState = CrewItemViewState(crewman.id,crewman.name, crewman.job)
    CrewItem(crewItemViewState = crewItemViewState)
}
