package com.example.glucoguardclient.ui.metics.result

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ResultScreen(posPercentage: Float) {
    val negPercentage = 1f - posPercentage
    val isHighRisk = posPercentage >= 0.5f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isHighRisk)
                "You are at high risk of diabetes"
            else
                "Nice! Low risk of diabetes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (isHighRisk) Color.Red else Color.Green
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${(posPercentage * 100).toInt()}%",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (isHighRisk) Color.Red else Color.Green
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isHighRisk)
                "Consult a doctor ASAP"
            else
                "Keep up the good work!",
            fontSize = 18.sp,
            color = if (isHighRisk) Color.Red else Color.Green
        )

        Spacer(modifier = Modifier.height(32.dp))

        CircularProgressBar(
            posPercentage = posPercentage,
            negPercentage = negPercentage,
            radius = 100.dp
        )
    }
}

@Composable
fun CircularProgressBar(
    posPercentage: Float,
    negPercentage: Float,
    radius: Dp
) {
    Canvas(
        modifier = Modifier.size(radius * 2f)
    ) {
        val strokeWidth = 20.dp.toPx()
        val center = Offset(size.width / 2f, size.height / 2f)
        val totalAngle = 360f
        val posAngle = posPercentage * totalAngle
        val negAngle = negPercentage * totalAngle

        drawArc(
            color = Color.LightGray,
            startAngle = 0f,
            sweepAngle = totalAngle,
            useCenter = false,
            topLeft = Offset.Zero,
            size = size,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        drawArc(
            color = Color.Green,
            startAngle = 270f,
            sweepAngle = negAngle,
            useCenter = false,
            topLeft = Offset.Zero,
            size = size,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        drawArc(
            color = Color.Red,
            startAngle = 270f + negAngle,
            sweepAngle = posAngle,
            useCenter = false,
            topLeft = Offset.Zero,
            size = size,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        val textRadius = radius.toPx() - strokeWidth / 2
        val posTextAngle = 270f + negAngle + posAngle / 2
        val negTextAngle = 270f + negAngle / 2

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "${(posPercentage * 100).toInt()}%",
                center.x + cos(posTextAngle * PI / 180f).toFloat() * textRadius,
                center.y + sin(posTextAngle * PI / 180f).toFloat() * textRadius,
                android.graphics.Paint().apply {
                    textSize = 16.sp.toPx()
                    color = android.graphics.Color.RED
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )

            drawText(
                "${(negPercentage * 100).toInt()}%",
                center.x + cos(negTextAngle * PI / 180f).toFloat() * textRadius,
                center.y + sin(negTextAngle * PI / 180f).toFloat() * textRadius,
                android.graphics.Paint().apply {
                    textSize = 16.sp.toPx()
                    color = android.graphics.Color.GREEN
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}



@Preview
@Composable
fun showPercentage(){
    ResultScreen(posPercentage = 0.56F)
}