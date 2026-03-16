package com.zebr.navigation

sealed class Screen(val route: String) {
    data object Menu : Screen("menu")
    data object Levels : Screen("levels")
    data object Game : Screen("game/{level}") {
        fun createRoute(level: Int) = "game/$level"
    }
    data object Settings : Screen("settings")
    data object HowToPlay : Screen("how_to_play")
    data object PrivacyPolicy : Screen("privacy_policy")
    data object Leaderboard : Screen("leaderboard")
    data object Loading : Screen("loading")
    data object Connect : Screen("connect")
}
