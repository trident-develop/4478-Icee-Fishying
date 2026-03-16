package com.zebr.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zebr.MainActivity
import com.zebr.R
import com.zebr.ui.components.MenuButton
import kotlin.math.sin

@SuppressLint("ContextCastToActivity")
@Composable
fun MenuScreen(
    onPlayClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLeaderboardClick: () -> Unit
) {
    val activity = LocalContext.current as? MainActivity
    val infiniteTransition = rememberInfiniteTransition(label = "menu")

    val fish1X by infiniteTransition.animateFloat(
        initialValue = -0.15f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fish1"
    )

    val fish2X by infiniteTransition.animateFloat(
        initialValue = 1.15f,
        targetValue = -0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "fish2"
    )

    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
    )

    val bubbleY by infiniteTransition.animateFloat(
        initialValue = 1.1f,
        targetValue = -0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubble"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Ambient fish swimming
        Image(
            painter = painterResource(R.drawable.fish_7),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .graphicsLayer {
                    translationX = fish1X * 1200f
                    translationY = 300f + sin((wavePhase + fish1X * 4).toDouble()).toFloat() * 20f
                    alpha = 0.6f
                },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(R.drawable.fish_3),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .graphicsLayer {
                    translationX = fish2X * 1200f
                    translationY = 600f + sin((wavePhase + fish2X * 3).toDouble()).toFloat() * 15f
                    scaleX = -1f
                    alpha = 0.5f
                },
            contentScale = ContentScale.Fit
        )

        // Bubbles
        Box(
            modifier = Modifier
                .size(10.dp)
                .graphicsLayer {
                    translationX = 150f
                    translationY = bubbleY * 1800f
                    alpha = 0.4f
                }
                .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .size(7.dp)
                .graphicsLayer {
                    translationX = 700f
                    translationY = bubbleY * 1800f + 400f
                    alpha = 0.3f
                }
                .background(Color.White.copy(alpha = 0.25f), shape = CircleShape)
        )

        // Decorative corals at bottom
        Image(
            painter = painterResource(R.drawable.coral_2),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomStart)
                .offset(x = 10.dp, y = (-5).dp)
                .graphicsLayer {
                    rotationZ = sin(wavePhase.toDouble()).toFloat() * 3f
                },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(R.drawable.coral_4),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-10).dp, y = (-5).dp)
                .graphicsLayer {
                    rotationZ = sin((wavePhase + 1.5f).toDouble()).toFloat() * 3f
                },
            contentScale = ContentScale.Fit
        )

        // Menu content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MenuButton(text = "Play", onClick = onPlayClick)
            MenuButton(text = "Leaderboard", fontSize = 26.sp, onClick = onLeaderboardClick)
            MenuButton(text = "Settings", onClick = onSettingsClick)
            MenuButton(text = "Exit", onClick = { activity?.finish() })
        }
    }
}