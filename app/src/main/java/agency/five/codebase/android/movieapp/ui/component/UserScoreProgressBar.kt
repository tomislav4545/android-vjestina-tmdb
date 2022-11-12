package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Green_Circle
import agency.five.codebase.android.movieapp.ui.theme.Green_Progress
import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val DEGREES_IN_CIRCLE = 360f
private const val PERCENTAGE_FACTOR= 10f
private const val STARTING_ANGLE= -90f

@Composable
fun UserScoreProgressBar(
    score:Float,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(60.dp)
    ){
        Canvas(
            modifier = Modifier.padding(5.dp).size(60.dp)
        ){
            drawArc(
                color = Green_Circle,
                startAngle = STARTING_ANGLE,
                sweepAngle = DEGREES_IN_CIRCLE,
                useCenter = false,
                style = Stroke(5.dp.toPx(),
                    cap = StrokeCap.Round)
            )
            drawArc(
                color = Green_Progress,
                startAngle = STARTING_ANGLE,
                sweepAngle = DEGREES_IN_CIRCLE * score,
                useCenter = false,
                style = Stroke(5.dp.toPx(),
                cap = StrokeCap.Round)
            )
        }

        Text(
            text = (score * PERCENTAGE_FACTOR).toString(),
            style = Typography.h3
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserScoreProgressBarPreview(){
    UserScoreProgressBar(score = 0.69f)
}
