package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)

sealed class MovieCategoryLabelTextViewState{
    class InputText(val text: String):MovieCategoryLabelTextViewState()
    class ResourceText(@StringRes val textRes: Int): MovieCategoryLabelTextViewState()
}

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    onClick: () -> Unit
){
    if(movieCategoryLabelViewState.isSelected){
        Column(
            modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max)
                               .clickable{onClick()}
        ){
            Text(
                text = selectTextSource(movieCategoryLabelViewState = movieCategoryLabelViewState)
            )
            Spacer(
                modifier = Modifier.size(5.dp)
            )
            Divider(
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                thickness = 3.dp
            )
        }
    }
    else{
        UnselectedMovieCategory(movieCategoryLabelViewState)
    }
}

@Composable
fun UnselectedMovieCategory(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier= Modifier
){
    Text(
        text = selectTextSource(movieCategoryLabelViewState = movieCategoryLabelViewState),
        modifier.alpha(ContentAlpha.disabled)
    )
}

@Composable
fun selectTextSource(
    movieCategoryLabelViewState: MovieCategoryLabelViewState
): String{
    return when(val categoryText = movieCategoryLabelViewState.categoryText) {
        is MovieCategoryLabelTextViewState.InputText -> categoryText.text
        is MovieCategoryLabelTextViewState.ResourceText -> stringResource(id = categoryText.textRes)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCategoryLabelPreview(){
    val inputText = MovieCategoryLabelTextViewState.InputText("Movies")
    val stringRes = MovieCategoryLabelTextViewState.ResourceText(R.string.app_name)
    val categoryViewState1 = MovieCategoryLabelViewState(2, false, inputText)
    val categoryViewState2 = MovieCategoryLabelViewState(1, true, stringRes)
    MovieCategoryLabel(movieCategoryLabelViewState = categoryViewState1, onClick = {})
}
