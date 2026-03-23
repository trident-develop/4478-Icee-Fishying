package photo.editor.photoeditor.filtersforpictu.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import photo.editor.photoeditor.filtersforpictu.LoadingActivity
import photo.editor.photoeditor.filtersforpictu.MainActivity
import photo.editor.photoeditor.filtersforpictu.screens.ConnectScreen
import photo.editor.photoeditor.filtersforpictu.screens.LoadingScreen
import photo.editor.photoeditor.filtersforpictu.screens.isFruitConnected
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import photo.editor.photoeditor.filtersforpictu.model.gray4.Gray4
import photo.editor.photoeditor.filtersforpictu.model.gray4.STUB_STORAGE_KEY
import photo.editor.photoeditor.filtersforpictu.model.gray4.STUB_STORAGE_VALUE_TRUE
import photo.editor.photoeditor.filtersforpictu.model.gray4.datastore.StorageImpl

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



//            LaunchedEffect(Unit) {
//                delay(2000)
//                context.startActivity(Intent(context, MainActivity::class.java))
//                context.finish()
//            }

            LoadingScreen()

            val toNoNet = {
                navController.navigate(Screen.Connect.route) {
                    popUpTo(Screen.Loading.route) { inclusive = true }
                }
            }

            val toStub = {
                context.startActivity(Intent(context, MainActivity::class.java))
                context.finish()
            }

            val internetCheck = {
                context.isFruitConnected()
            }


            Gray4(toStub = {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("TAGG", "Save Stub TRUE")
                    val storage = StorageImpl.getInstance(context)
                    storage.putString(STUB_STORAGE_KEY, STUB_STORAGE_VALUE_TRUE)
                }
                toStub()
            }, toNoNet = toNoNet,
                internetCheck)

        }

        composable(Screen.Connect.route) {
            ConnectScreen(navController)
        }
    }
}