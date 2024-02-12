import android.content.Context
import android.media.MediaPlayer
import com.example.personaje.R

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun init(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.temita)
        }
    }

    fun start() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
