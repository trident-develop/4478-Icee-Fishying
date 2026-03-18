package photo.editor.photoeditor.filtersforpictu.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import photo.editor.photoeditor.filtersforpictu.R
import photo.editor.photoeditor.filtersforpictu.model.LevelConfig
import photo.editor.photoeditor.filtersforpictu.model.SwimmingFish
import photo.editor.photoeditor.filtersforpictu.ui.components.MenuButton
import photo.editor.photoeditor.filtersforpictu.ui.components.SquareButton
import photo.editor.photoeditor.filtersforpictu.ui.theme.GameFont
import photo.editor.photoeditor.filtersforpictu.ui.theme.TealDark
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.text.get

@Composable
fun GameScreen(
    level: Int,
    soundEnabled: Boolean,
    onPlayCollectSound: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onReplayClick: () -> Unit,
    onNextLevel: () -> Unit,
    onLevelComplete: (score: Int) -> Unit
) {
    val config = remember(level) { LevelConfig.forLevel(level) }
    var score by remember { mutableIntStateOf(0) }
    var lives by remember { mutableIntStateOf(3) }
    var isPaused by remember { mutableStateOf(false) }
    var isGameOver by remember { mutableStateOf(false) }
    var isLevelComplete by remember { mutableStateOf(false) }
    val fishes = remember { mutableStateListOf<SwimmingFish>() }
    var nextFishId by remember { mutableIntStateOf(0) }

    var isExitingScreen by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        isExitingScreen = true
        onBackClick()
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE && !isGameOver && !isLevelComplete && !isExitingScreen)
                isPaused = true
        }
        lifecycleOwner.lifecycle.addObserver(obs)
        onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
    }

    // Game loop
    LaunchedEffect(isPaused, isGameOver, isLevelComplete) {
        if (isPaused || isGameOver || isLevelComplete) return@LaunchedEffect

        var lastSpawnTime = 0L

        while (true) {
            val now = System.currentTimeMillis()
            val deltaSeconds = 0.016f

            // Spawn new fish
            if (now - lastSpawnTime > config.spawnIntervalMs && fishes.size < config.maxFish) {
                val goingRight = Random.nextBoolean()
                val isGood = Random.nextFloat() < 0.6f
                val fishType = if (isGood) config.goodFish else config.badFish
                val speedVariation = config.baseSpeed * (1f + Random.nextFloat() * 0.5f)

                val newFish = SwimmingFish(
                    id = nextFishId++,
                    fishType = fishType,
                    isGood = isGood,
                    xFraction = if (goingRight) -0.15f else 1.15f,
                    yFraction = 0.2f + Random.nextFloat() * 0.55f,
                    speed = speedVariation,
                    goingRight = goingRight
                )
                fishes.add(newFish)
                lastSpawnTime = now
            }

            // Update fish positions
            val toRemove = mutableListOf<Int>()
            for (i in fishes.indices) {
                val fish = fishes[i]
                val newX = if (fish.goingRight) {
                    fish.xFraction + fish.speed * deltaSeconds
                } else {
                    fish.xFraction - fish.speed * deltaSeconds
                }
                fishes[i] = fish.copy(xFraction = newX)

                // Remove off-screen fish
                if (newX < -0.2f || newX > 1.2f) {
                    toRemove.add(fish.id)
                }
            }
            fishes.removeAll { it.id in toRemove }

            delay(16)
        }
    }

    // Handle fish tap
    fun onFishTapped(fish: SwimmingFish) {
        if (isPaused || isGameOver || isLevelComplete) return

        fishes.removeAll { it.id == fish.id }

        if (fish.isGood) {
            score++
            if (soundEnabled) onPlayCollectSound()
            if (score >= config.targetScore) {
                isLevelComplete = true
                onLevelComplete(score)
            }
        } else {
            lives--
            if (soundEnabled) onPlayCollectSound()
            if (lives <= 0) {
                lives = 0
                isGameOver = true
            }
        }
    }

    // Reset game state
    fun resetGame() {
        score = 0
        lives = 3
        isPaused = false
        isGameOver = false
        isLevelComplete = false
        fishes.clear()
        nextFishId = 0
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Bottom decorations
        Image(
            painter = painterResource(R.drawable.coral_1),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.BottomStart)
                .offset(x = 10.dp, y = (-5).dp),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(R.drawable.coral_2),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-15).dp, y = (-5).dp),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(R.drawable.shell_2),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-5).dp),
            contentScale = ContentScale.Fit
        )

        // Game area with swimming fish
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp, bottom = 20.dp)
        ) {
            val areaWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
            val areaHeightPx = with(LocalDensity.current) { maxHeight.toPx() }
            val fishSizeDp = 65.dp
            val fishSizePx = with(LocalDensity.current) { fishSizeDp.toPx() }

            fishes.forEach { fish ->
                val xPx = fish.xFraction * areaWidthPx - fishSizePx / 2
                val yPx = fish.yFraction * areaHeightPx - fishSizePx / 2
                val xDp: Dp
                val yDp: Dp
                with(LocalDensity.current) {
                    xDp = xPx.toDp()
                    yDp = yPx.toDp()
                }

                Image(
                    painter = painterResource(fish.fishType.drawableRes),
                    contentDescription = if (fish.isGood) "Good fish" else "Bad fish",
                    modifier = Modifier
                        .offset(x = xDp, y = yDp)
                        .size(fishSizeDp)
                        .graphicsLayer {
                            scaleX = if (fish.goingRight) 1f else -1f
                        }
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onFishTapped(fish) },
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Top HUD
        GameHud(
            score = score,
            targetScore = config.targetScore,
            lives = lives,
            level = level,
            goodFishRes = config.goodFish.drawableRes,
            badFishRes = config.badFish.drawableRes,
            onPauseClick = { isPaused = true },
            onBackClick = {
                isExitingScreen = true
                onBackClick()
            },
            onHomeClick = {
                isExitingScreen = true
                onHomeClick()
            },
            onReplayClick = {
                resetGame()
            }
        )

        // Pause overlay
        if (isPaused) {
            GameOverlay(
                title = "Paused",
                onResume = { isPaused = false },
                onReplay = {
                    resetGame()
                },
                onHome = {
                    isExitingScreen = true
                    onHomeClick()
                }
            )
        }

        // Game over overlay
        if (isGameOver) {
            GameResultOverlay(
                title = "Game Over",
                subtitle = "Score: $score",
                showNext = false,
                onReplay = {
                    resetGame()
                },
                onHome = {
                    isExitingScreen = true
                    onHomeClick()
                }
            )
        }

        // Level complete overlay
        if (isLevelComplete) {
            GameResultOverlay(
                title = "Level Complete!",
                subtitle = "Score: $score / ${config.targetScore}",
                showNext = level < 30,
                onNext = {
                    isExitingScreen = true
                    onNextLevel()
                },
                onReplay = {
                    resetGame()
                },
                onHome = {
                    isExitingScreen = true
                    onHomeClick()
                }
            )
        }
    }
}

@Composable
private fun GameHud(
    score: Int,
    targetScore: Int,
    lives: Int,
    level: Int,
    goodFishRes: Int,
    badFishRes: Int,
    onPauseClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onReplayClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
    ) {
        // Control buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.12f,
                    btnClickable = onBackClick
                )
            }

            Text(
                text = "Lv. $level",
                color = TealDark,
                fontSize = 30.sp,
                fontFamily = GameFont
            )

            SquareButton(
                btnRes = R.drawable.pause_button,
                btnMaxWidth = 0.16f,
                btnClickable = onPauseClick
            )
        }

        // Score and lives row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Score display
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.score_bg),
                    contentDescription = null,
                    modifier = Modifier.height(36.dp),
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    text = "$score / $targetScore",
                    color = TealDark,
                    fontSize = 16.sp,
                    fontFamily = GameFont,
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                )
            }

            // Lives
            Text(
                text = "❤".repeat(lives) + "♡".repeat((3 - lives).coerceAtLeast(0)),
                fontSize = 20.sp,
                color = Color.Red
            )
        }

        // Fish info row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Good fish indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Tap: ",
                    color = Color(0xFF4CAF50),
                    fontSize = 13.sp,
                    fontFamily = GameFont
                )
                Image(
                    painter = painterResource(goodFishRes),
                    contentDescription = "Good fish",
                    modifier = Modifier.size(30.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Bad fish indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Avoid: ",
                    color = Color(0xFFF44336),
                    fontSize = 13.sp,
                    fontFamily = GameFont
                )
                Image(
                    painter = painterResource(badFishRes),
                    contentDescription = "Bad fish",
                    modifier = Modifier.size(30.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun GameOverlay(
    title: String,
    onResume: () -> Unit,
    onReplay: () -> Unit,
    onHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { /* consume clicks */ },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 40.sp,
                fontFamily = GameFont
            )
            Spacer(modifier = Modifier.height(16.dp))
            MenuButton(text = "Resume", onClick = onResume)
            MenuButton(text = "Replay", onClick = onReplay)
            MenuButton(text = "Home", onClick = onHome)
        }
    }
}

@Composable
private fun GameResultOverlay(
    title: String,
    subtitle: String,
    showNext: Boolean = false,
    onNext: () -> Unit = {},
    onReplay: () -> Unit,
    onHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { /* consume clicks */ },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 36.sp,
                fontFamily = GameFont
            )
            Text(
                text = subtitle,
                color = Color(0xFF4FC3F7),
                fontSize = 26.sp,
                fontFamily = GameFont
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (showNext) {
                MenuButton(text = "Next Level", fontSize = 20.sp, onClick = onNext)
            }
            MenuButton(text = "Replay", onClick = onReplay)
            MenuButton(text = "Home", onClick = onHome)
        }
    }
}