package photo.editor.photoeditor.filtersforpictu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import photo.editor.photoeditor.filtersforpictu.model.gray4.TRACKING_ID
import photo.editor.photoeditor.filtersforpictu.model.gray4.push.PushRegistrationManager
import photo.editor.photoeditor.filtersforpictu.navigation.LoadingGraph

class LoadingActivity : ComponentActivity() {
    private var controller: WindowInsetsControllerCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        controller = WindowInsetsControllerCompat(window, window.decorView)
        controller?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller?.hide(WindowInsetsCompat.Type.systemBars())
        enableEdgeToEdge()

        val trakId = this.intent.getStringExtra(TRACKING_ID)
        if(trakId != null) {
            val pushRegistrationManager = PushRegistrationManager(this)
            lifecycleScope.launch {
                pushRegistrationManager.sendPostback(trakId)
            }
        }
        setContent {
            LoadingGraph()
        }
    }
}
