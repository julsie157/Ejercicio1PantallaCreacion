package com.example.personaje



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class EnemigoActivity : AppCompatActivity() {

    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    private lateinit var playButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_enemigo)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)


        MusicPlayer.init(this)
        playButton = findViewById<Button>(R.id.play_button)

        MusicPlayer.init(this)
        updatePlayButton()

        playButton.setOnClickListener {
            if (MusicPlayer.isPlaying()) {
                MusicPlayer.pause()
            } else {
                MusicPlayer.start()
            }
            updatePlayButton()
        }




        val lucharButton = findViewById<Button>(R.id.BotonlucharoHuir)
        lucharButton.setOnClickListener {
            if (Random.nextBoolean()) {
                val intent = Intent(this, CombateActivity::class.java).apply {
                    putExtra("intentExtraIdPersonaje", idPersonaje)
                }
                startActivity(intent)
                finish()
            } else {
                finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (MusicPlayer.isPlaying()) {
            MusicPlayer.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (MusicPlayer.isPlaying()) {
            MusicPlayer.start()
        }
    }

    private fun updatePlayButton() {
        if (MusicPlayer.isPlaying()) {
            playButton.text = "Mute"
        } else {
            playButton.text = "Sound"
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        MusicPlayer.release()
    }

}