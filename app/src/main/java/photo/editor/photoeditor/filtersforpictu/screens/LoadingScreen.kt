package photo.editor.photoeditor.filtersforpictu.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import photo.editor.photoeditor.filtersforpictu.R
import photo.editor.photoeditor.filtersforpictu.ui.theme.GameFont
import photo.editor.photoeditor.filtersforpictu.ui.theme.TealDark
import kotlin.math.sin

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    BackHandler(enabled = true) {}
    val fish1X by infiniteTransition.animateFloat(
        initialValue = -0.2f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fish1X"
    )

    val fish2X by infiniteTransition.animateFloat(
        initialValue = 1.2f,
        targetValue = -0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fish2X"
    )

    val fish3X by infiniteTransition.animateFloat(
        initialValue = -0.2f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fish3X"
    )

    val bubbleY by infiniteTransition.animateFloat(
        initialValue = 1.1f,
        targetValue = -0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubbleY"
    )

    val bubble2Y by infiniteTransition.animateFloat(
        initialValue = 1.1f,
        targetValue = -0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubble2Y"
    )

    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D47A1),
                        Color(0xFF1565C0),
                        Color(0xFF0277BD),
                        Color(0xFF004D66)
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Decorative corals at bottom
        Image(
            painter = painterResource(R.drawable.coral_1),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomStart)
                .offset(x = 20.dp, y = (-10).dp)
                .graphicsLayer {
                    rotationZ = sin(wavePhase.toDouble()).toFloat() * 5f
                },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(R.drawable.coral_3),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-20).dp, y = (-10).dp)
                .graphicsLayer {
                    rotationZ = sin((wavePhase + 1f).toDouble()).toFloat() * 5f
                },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(R.drawable.shell_1),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.BottomCenter)
                .offset(x = 40.dp, y = (-5).dp),
            contentScale = ContentScale.Fit
        )

        // Swimming fish 1 - left to right, with wave motion
        Image(
            painter = painterResource(R.drawable.fish_3),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .graphicsLayer {
                    translationX = fish1X * 1200f
                    translationY = 500f + sin((wavePhase + fish1X * 4).toDouble()).toFloat() * 40f
                },
            contentScale = ContentScale.Fit
        )

        // Swimming fish 2 - right to left
        Image(
            painter = painterResource(R.drawable.fish_6),
            contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .graphicsLayer {
                    translationX = fish2X * 1200f
                    translationY = 350f + sin((wavePhase + fish2X * 3).toDouble()).toFloat() * 30f
                    scaleX = -1f
                },
            contentScale = ContentScale.Fit
        )

        // Swimming fish 3 - left to right, lower
        Image(
            painter = painterResource(R.drawable.fish_7),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .graphicsLayer {
                    translationX = fish3X * 1200f
                    translationY = 700f + sin((wavePhase + fish3X * 5).toDouble()).toFloat() * 25f
                },
            contentScale = ContentScale.Fit
        )

        // Bubbles
        Box(
            modifier = Modifier
                .size(12.dp)
                .graphicsLayer {
                    translationX = 200f
                    translationY = bubbleY * 1800f
                    alpha = 0.6f
                }
                .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
        )

        Box(
            modifier = Modifier
                .size(8.dp)
                .graphicsLayer {
                    translationX = 600f
                    translationY = bubble2Y * 1800f
                    alpha = 0.5f
                }
                .background(Color.White.copy(alpha = 0.25f), shape = CircleShape)
        )

        Box(
            modifier = Modifier
                .size(10.dp)
                .graphicsLayer {
                    translationX = 400f
                    translationY = bubble2Y * 1800f + 300f
                    alpha = 0.4f
                }
                .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
        )

        // Loading indicator
        CircularProgressIndicator(
            color = TealDark,
            strokeWidth = 6.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-80).dp)
                .size(60.dp)
        )

        Text(
            text = "Loading...",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 28.sp,
            fontFamily = GameFont,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-45).dp)
        )
    }
}