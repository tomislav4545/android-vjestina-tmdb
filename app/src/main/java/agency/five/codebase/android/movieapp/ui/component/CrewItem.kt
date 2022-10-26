package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getCrewman
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

data class CrewItemViewState(
    val name: String,
    val job: String
)

@Composable
fun CrewItem(crewItemViewState: CrewItemViewState){
    Column {
        Text(text = crewItemViewState.name, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = crewItemViewState.job, fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
private fun CrewmanDetailsPreview() {
    val crewman = getCrewman()
    val crewItemViewState = CrewItemViewState(crewman.name,crewman.job)
    CrewItem(crewItemViewState = crewItemViewState)
}