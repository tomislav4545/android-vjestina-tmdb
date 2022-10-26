package agency.five.codebase.android.movieapp.ui.component


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
import androidx.compose.ui.unit.sp

@Composable
fun UserScoreProgressBar(score:Float){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(60.dp)
    ){
        Canvas(modifier = Modifier.padding(5.dp).size(60.dp)){
            drawArc(color = Color(0xFF9BE2AD),startAngle = -90f,sweepAngle = 360f,useCenter = false,style = Stroke(5.dp.toPx(), cap = StrokeCap.Round))
            drawArc(color = Color(0xFF1CAD66),startAngle = -90f,sweepAngle = 360 * score,useCenter = false,style = Stroke(5.dp.toPx(), cap = StrokeCap.Round))
        }

        Text(text = (score * 10f).toString(),fontSize = 15.sp,color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun UserScoreProgressBarPreview(){
    UserScoreProgressBar(score = 0.69f)
}