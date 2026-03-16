package com.zebr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zebr.screens.GameScreen
import com.zebr.screens.HowToPlayScreen
import com.zebr.screens.LeaderboardScreen
import com.zebr.screens.LevelsScreen
import com.zebr.screens.MenuScreen
import com.zebr.screens.PrivacyPolicyScreen
import com.zebr.screens.SettingsScreen
import com.zebr.utils.PreferenceManager

@Composable
fun AppNavGraph(
    navController: NavHostController,
    preferenceManager: PreferenceManager,
    musicEnabled: Boolean,
    soundEnabled: Boolean,
    onMusicToggle: () -> Unit,
    onSoundToggle: () -> Unit,
    onPlayCollectSound: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Menu.route) {
            MenuScreen(
                onPlayClick = {
                    navController.navigate(Screen.Levels.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onLeaderboardClick = {
                    navController.navigate(Screen.Leaderboard.route)
                }
            )
        }

        composable(Screen.Levels.route) {
            var unlockedLevel by remember { mutableIntStateOf(preferenceManager.unlockedLevel) }

            LevelsScreen(
                unlockedLevel = unlockedLevel,
                onLevelClick = { level ->
                    navController.navigate(Screen.Game.createRoute(level))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 1

            GameScreen(
                level = level,
                soundEnabled = soundEnabled,
                onPlayCollectSound = onPlayCollectSound,
                onBackClick = {
                    navController.popBackStack()
                },
                onHomeClick = {
                    navController.popBackStack(Screen.Menu.route, inclusive = false)
                },
                onReplayClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Game.createRoute(level))
                },
                onNextLevel = {
                    val next = (level + 1).coerceAtMost(30)
                    navController.popBackStack()
                    navController.navigate(Screen.Game.createRoute(next))
                },
                onLevelComplete = { score ->
                    val nextLevel = level + 1
                    if (nextLevel > preferenceManager.unlockedLevel) {
                        preferenceManager.unlockedLevel = nextLevel
                    }
                    preferenceManager.setLevelBestScore(level, score)
                    val totalBest = preferenceManager.bestScore
                    if (score > totalBest) {
                        preferenceManager.bestScore = score
                    }
                    if (level > preferenceManager.highestLevel) {
                        preferenceManager.highestLevel = level
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                musicEnabled = musicEnabled,
                soundEnabled = soundEnabled,
                onMusicToggle = onMusicToggle,
                onSoundToggle = onSoundToggle,
                onHowToPlayClick = {
                    navController.navigate(Screen.HowToPlay.route)
                },
                onPrivacyPolicyClick = {
                    navController.navigate(Screen.PrivacyPolicy.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.HowToPlay.route) {
            HowToPlayScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(
                unlockedLevel = preferenceManager.unlockedLevel,
                bestScore = preferenceManager.bestScore,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
