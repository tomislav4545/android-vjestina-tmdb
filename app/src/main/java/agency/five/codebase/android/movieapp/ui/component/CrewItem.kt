package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getCrewman
import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

data class CrewItemViewState(
    val name: String,
    val job: String
)

@Composable
fun CrewItem(
    crewItemViewState: CrewItemViewState,
    modifier: Modifier = Modifier
){
    Column {
        Text(
            text = crewItemViewState.name,
            textAlign = TextAlign.Left,
            style = Typography.h3,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = crewItemViewState.job,
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
    val crewItemViewState = CrewItemViewState(crewman.name,crewman.job)
    CrewItem(crewItemViewState = crewItemViewState)
}
