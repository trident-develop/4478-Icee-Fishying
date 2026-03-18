package photo.editor.photoeditor.filtersforpictu.utils

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import photo.editor.photoeditor.filtersforpictu.R

class AudioManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var soundPool: SoundPool? = null
    private var collectSoundId: Int = 0
    private var isMusicPlaying = false

    fun initSounds() {
        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .build()
        collectSoundId = soundPool?.load(context, R.raw.collect_fish, 1) ?: 0
    }

    fun startMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.game_music).apply {
                isLooping = true
                setVolume(0.5f, 0.5f)
            }
        }
        mediaPlayer?.start()
        isMusicPlaying = true
    }

    fun stopMusic() {
        mediaPlayer?.pause()
        isMusicPlaying = false
    }

    fun resumeMusic() {
        if (isMusicPlaying) {
            mediaPlayer?.start()
        }
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun playCollectSound() {
        soundPool?.play(collectSoundId, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        soundPool?.release()
        soundPool = null
        isMusicPlaying = false
    }
}
