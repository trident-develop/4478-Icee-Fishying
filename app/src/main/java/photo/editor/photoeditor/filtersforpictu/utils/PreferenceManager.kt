package photo.editor.photoeditor.filtersforpictu.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("icee_fishying_prefs", Context.MODE_PRIVATE)

    var musicEnabled: Boolean
        get() = prefs.getBoolean(KEY_MUSIC, true)
        set(value) = prefs.edit { putBoolean(KEY_MUSIC, value) }

    var soundEnabled: Boolean
        get() = prefs.getBoolean(KEY_SOUND, true)
        set(value) = prefs.edit { putBoolean(KEY_SOUND, value) }

    var unlockedLevel: Int
        get() = prefs.getInt(KEY_UNLOCKED_LEVEL, 1)
        set(value) = prefs.edit { putInt(KEY_UNLOCKED_LEVEL, value) }

    var bestScore: Int
        get() = prefs.getInt(KEY_BEST_SCORE, 0)
        set(value) = prefs.edit { putInt(KEY_BEST_SCORE, value) }

    var highestLevel: Int
        get() = prefs.getInt(KEY_HIGHEST_LEVEL, 0)
        set(value) = prefs.edit { putInt(KEY_HIGHEST_LEVEL, value) }

    fun getLevelBestScore(level: Int): Int =
        prefs.getInt("${KEY_LEVEL_SCORE_PREFIX}$level", 0)

    fun setLevelBestScore(level: Int, score: Int) {
        val current = getLevelBestScore(level)
        if (score > current) {
            prefs.edit { putInt("${KEY_LEVEL_SCORE_PREFIX}$level", score) }
        }
    }

    companion object {
        private const val KEY_MUSIC = "music_enabled"
        private const val KEY_SOUND = "sound_enabled"
        private const val KEY_UNLOCKED_LEVEL = "unlocked_level"
        private const val KEY_BEST_SCORE = "best_score"
        private const val KEY_HIGHEST_LEVEL = "highest_level"
        private const val KEY_LEVEL_SCORE_PREFIX = "level_score_"
    }
}
