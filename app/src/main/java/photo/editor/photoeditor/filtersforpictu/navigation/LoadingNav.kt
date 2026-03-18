package photo.editor.photoeditor.filtersforpictu.navigation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import photo.editor.photoeditor.filtersforpictu.LoadingActivity
import photo.editor.photoeditor.filtersforpictu.MainActivity
import photo.editor.photoeditor.filtersforpictu.screens.ConnectScreen
import photo.editor.photoeditor.filtersforpictu.screens.LoadingScreen
import photo.editor.photoeditor.filtersforpictu.screens.isFruitConnected
import kotlinx.coroutines.delay

@SuppressLint("ContextCastToActivity")
@Composable
fun LoadingGraph() {

    val navController = rememberNavController()
    val context = LocalContext.current as LoadingActivity

    NavHost(
        navController = navController,
        startDestination = if (context.isFruitConnected()) Screen.Loading.route else Screen.Connect.route
    ) {
        composable(Screen.Loading.route) {

            LaunchedEffect(Unit) {
                delay(2000)
                context.startActivity(Intent(context, MainActivity::class.java))
                context.finish()
            }

            LoadingScreen()
        }

        composable(Screen.Connect.route) {
            ConnectScreen(navController)
        }
    }
}