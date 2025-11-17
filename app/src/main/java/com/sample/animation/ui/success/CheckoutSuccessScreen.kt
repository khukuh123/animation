package com.sample.animation.ui.success

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.matchParentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class ConfettiItem(
    val xPosition: Float,
    val size: Float,
    val speed: Float,
    val color: Color,
    val shape: ConfettiShape
)

enum class ConfettiShape { Circle, Rectangle, Triangle }

@Composable
fun CheckoutSuccessScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val confetti = remember {
        List(Random.nextInt(15, 25)) {
            ConfettiItem(
                xPosition = Random.nextFloat(),
                size = Random.nextFloat().coerceIn(6f, 18f),
                speed = Random.nextFloat().coerceIn(30f, 90f),
                color = listOf(
                    Color(0xFFFF6F61),
                    Color(0xFF64B5F6),
                    Color(0xFF81C784),
                    Color(0xFFFFD54F)
                ).random(),
                shape = ConfettiShape.values().random()
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "ConfettiTransition")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 4000, easing = LinearEasing)),
        label = "Progress"
    )

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Payment Successful!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    confetti.forEachIndexed { index, piece ->
                        val yOffset = (size.height * animationProgress + piece.speed * index) % size.height
                        val x = piece.xPosition * size.width
                        val rotation = (animationProgress * 360f + index * 15) % 360f
                        rotate(degrees = rotation, pivot = Offset(x, yOffset)) {
                            when (piece.shape) {
                                ConfettiShape.Circle -> drawCircle(
                                    color = piece.color,
                                    radius = piece.size,
                                    center = Offset(x, yOffset)
                                )
                                ConfettiShape.Rectangle -> drawRect(
                                    color = piece.color,
                                    topLeft = Offset(x - piece.size / 2, yOffset - piece.size / 2),
                                    size = androidx.compose.ui.geometry.Size(piece.size, piece.size * 1.4f)
                                )
                                ConfettiShape.Triangle -> {
                                    val path = Path().apply {
                                        moveTo(x, yOffset - piece.size)
                                        lineTo(x - piece.size, yOffset + piece.size)
                                        lineTo(x + piece.size, yOffset + piece.size)
                                        close()
                                    }
                                    drawPath(path = path, color = piece.color)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onContinue) {
                Text(text = "Back to Home")
            }
        }
    }
}
