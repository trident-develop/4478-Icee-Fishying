package com.zebr.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.zebr.ui.components.pressableWithCooldown
import com.zebr.ui.theme.GameFont
import com.zebr.ui.theme.TealDark

@Composable
fun LevelsScreen(
    unlockedLevel: Int,
    onLevelClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
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
            // Top bar with back button
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
                    text = "Levels",
                    color = TealDark,
                    fontSize = 36.sp,
                    fontFamily = GameFont,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grid of levels
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(30) { index ->
                    val level = index + 1
                    val isUnlocked = level <= unlockedLevel

                    LevelItem(
                        level = level,
                        isUnlocked = isUnlocked,
                        onClick = { if (isUnlocked) onLevelClick(level) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelItem(
    level: Int,
    isUnlocked: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .pressableWithCooldown(
                onClick = onClick,
                enabled = isUnlocked
            )
    ) {
        Image(
            painter = painterResource(
                if (isUnlocked) R.drawable.level_open_button
                else R.drawable.level_close_button
            ),
            contentDescription = "Level $level",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            alpha = if (isUnlocked) 1f else 0.7f
        )

        Text(
            text = "$level",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = GameFont
        )
    }
}