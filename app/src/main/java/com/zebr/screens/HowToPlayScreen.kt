package com.zebr.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.zebr.R
import com.zebr.ui.components.SquareButton
import com.zebr.ui.theme.GameFont
import com.zebr.ui.theme.TealDark

@Composable
fun HowToPlayScreen(
    onBackClick: () -> Unit
) {
    val isInPreview = LocalInspectionMode.current
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
                .padding(top = 40.dp, bottom = 12.dp),
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
                    text = "How To Play",
                    color = TealDark,
                    fontSize = 32.sp,
                    fontFamily = GameFont,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 32.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(TealDark.copy(0.7f))
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Rule 1: Tap the right fish
                    RuleItem(
                        iconRes = R.drawable.fish_3,
                        title = "Catch the right fish!",
                        description = "Each level shows you which fish to tap. Tap on the correct fish to earn points!"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Rule 2: Avoid dangerous creatures
                    RuleItem(
                        iconRes = R.drawable.jellyfish,
                        title = "Avoid dangerous creatures!",
                        description = "Don't tap on jellyfish, octopus, or crabs! Tapping the wrong creature costs you a life."
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Rule 3: Lives
                    InfoBlock(
                        title = "Lives",
                        description = "You start each level with 3 lives. Lose all lives and it's game over!"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Rule 4: Score target
                    InfoBlock(
                        title = "Score Target",
                        description = "Reach the target score to complete the level and unlock the next one."
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Rule 5: Difficulty
                    InfoBlock(
                        title = "Difficulty",
                        description = "Each level gets harder — fish swim faster and you need more points. Stay sharp!"
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }

                if (!isInPreview) {
                    AndroidView(
                        factory = {
                            val adView = AdView(it)
                            adView.setAdSize(AdSize.BANNER)
                            adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
                            adView.loadAd(AdRequest.Builder().build())
                            adView
                        },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

@Composable
private fun RuleItem(
    iconRes: Int,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = GameFont
            )
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                fontFamily = GameFont
            )
        }
    }
}

@Composable
private fun InfoBlock(
    title: String,
    description: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = Color(0xFF4FC3F7),
            fontSize = 20.sp,
            fontFamily = GameFont
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 14.sp,
            fontFamily = GameFont
        )
    }
}