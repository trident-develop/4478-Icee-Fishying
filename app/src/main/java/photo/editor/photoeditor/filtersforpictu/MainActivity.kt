package photo.editor.photoeditor.filtersforpictu

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import photo.editor.photoeditor.filtersforpictu.navigation.AppNavGraph
import photo.editor.photoeditor.filtersforpictu.ui.theme.IceeFishyingTheme
import photo.editor.photoeditor.filtersforpictu.utils.AudioManager
import photo.editor.photoeditor.filtersforpictu.utils.PreferenceManager

class MainActivity : ComponentActivity() {

    private lateinit var audioManager: AudioManager
    private lateinit var preferenceManager: PreferenceManager
    private var musicEnabled by mutableStateOf(true)
    private var soundEnabled by mutableStateOf(true)
    private val windowController by lazy {
        WindowInsetsControllerCompat(window, window.decorView)
    }
    private var multiTouchDetected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        preferenceManager = PreferenceManager(this)
        audioManager = AudioManager(this)
        audioManager.initSounds()

        musicEnabled = preferenceManager.musicEnabled
        soundEnabled = preferenceManager.soundEnabled

        setContent {
            IceeFishyingTheme(dynamicColor = false) {
                val navController = rememberNavController()

                LaunchedEffect(musicEnabled) {
                    if (musicEnabled) {
                        audioManager.startMusic()
                    } else {
                        audioManager.stopMusic()
                    }
                }

                AppNavGraph(
                    navController = navController,
                    preferenceManager = preferenceManager,
                    musicEnabled = musicEnabled,
                    soundEnabled = soundEnabled,
                    onMusicToggle = {
                        musicEnabled = !musicEnabled
                        preferenceManager.musicEnabled = musicEnabled
                    },
                    onSoundToggle = {
                        soundEnabled = !soundEnabled
                        preferenceManager.soundEnabled = soundEnabled
                    },
                    onPlayCollectSound = {
                        if (soundEnabled) {
                            audioManager.playCollectSound()
                        }
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        windowController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowController.hide(WindowInsetsCompat.Type.systemBars())
        if (::audioManager.isInitialized && musicEnabled) {
            audioManager.resumeMusic()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::audioManager.isInitialized) {
            audioManager.pauseMusic()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::audioManager.isInitialized) {
            audioManager.release()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.pointerCount > 1) {
            if (!multiTouchDetected) {
                multiTouchDetected = true
                val cancelEvent = MotionEvent.obtain(ev)
                cancelEvent.action = MotionEvent.ACTION_CANCEL
                super.dispatchTouchEvent(cancelEvent)
                cancelEvent.recycle()
            }
            return true
        }
        if (multiTouchDetected) {
            if (ev.actionMasked == MotionEvent.ACTION_UP ||
                ev.actionMasked == MotionEvent.ACTION_CANCEL
            ) {
                multiTouchDetected = false
            }
            return true
        }
        return super.dispatchTouchEvent(ev)
    }
}
