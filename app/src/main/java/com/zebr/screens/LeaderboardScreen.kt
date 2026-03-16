package com.zebr.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zebr.R
import com.zebr.ui.components.SquareButton
import com.zebr.ui.theme.GameFont
import com.zebr.ui.theme.TealDark

@Composable
fun LeaderboardScreen(
    unlockedLevel: Int,
    bestScore: Int,
    onBackClick: () -> Unit
) {
    val mockEntries = listOf(
        LeaderboardEntry("Captain Nemo", 2500, 30),
        LeaderboardEntry("Pearl Diver", 1800, 25),
        LeaderboardEntry("Sea Hunter", 1200, 20),
        LeaderboardEntry("Wave Rider", 800, 15),
        LeaderboardEntry("Shell Seeker", 500, 10)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.12f,
                    modifier = Modifier.align(Alignment.CenterStart),
                    btnClickable = onBackClick
                )

                Text(
                    text = "Leaderboard",
                    color = TealDark,
                    fontSize = 32.sp,
                    fontFamily = GameFont,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Player stats card
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(
                        TealDark.copy(alpha = 0.7f),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Progress",
                        color = Color(0xFF4FC3F7),
                        fontSize = 22.sp,
                        fontFamily = GameFont
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(label = "Level", value = "$unlockedLevel / 30")
                        StatItem(label = "Best Score", value = "$bestScore")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Top Players",
                color = TealDark,
                fontSize = 22.sp,
                fontFamily = GameFont
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mock leaderboard
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                mockEntries.forEachIndexed { index, entry ->
                    LeaderboardRow(rank = index + 1, entry = entry)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = GameFont
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontFamily = GameFont
        )
    }
}

@Composable
private fun LeaderboardRow(rank: Int, entry: LeaderboardEntry) {
    val rankColor = when (rank) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#$rank",
            color = rankColor,
            fontSize = 20.sp,
            fontFamily = GameFont,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.name,
                color = TealDark,
                fontSize = 16.sp,
                fontFamily = GameFont
            )
            Text(
                text = "Level ${entry.level}",
                color = TealDark.copy(alpha = 0.6f),
                fontSize = 12.sp,
                fontFamily = GameFont
            )
        }
        Text(
            text = "${entry.score}",
            color = TealDark,
            fontSize = 18.sp,
            fontFamily = GameFont
        )
    }
}

private data class LeaderboardEntry(
    val name: String,
    val score: Int,
    val level: Int
)