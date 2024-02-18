package com.example.personaje

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale

class PantallaDado : AppCompatActivity(), OnInitListener {
    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    private lateinit var playButton: Button
    private lateinit var tts: TextToSpeech
    private lateinit var dado: ImageView
    private lateinit var Botontirar: Button
    private lateinit var Infotext: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this,this)
        setContentView(R.layout.layout_dado)
        dado = findViewById(R.id.dice_image)
        Infotext = findViewById(R.id.info_text)
        MusicPlayer.init(this)
        playButton = findViewById(R.id.play_button)
        Botontirar = findViewById(R.id.Botontirar)
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

        val dadoButton = findViewById<Button>(R.id.Botontirar)
        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje",-1L)

        dadoButton.setOnClickListener {
            val encounter = randomEncounter()
            val intent = when (encounter) {
                "Objeto" -> Intent(this, ObjetoActivity::class.java)
                "Ciudad" -> Intent(this, CiudadActivity::class.java)
                "Mercader" -> Intent(this, MercaderActivity::class.java)
                "Enemigo" -> Intent(this, EnemigoActivity::class.java)
                "Interactuar" -> Intent(this, InteractuarMascota::class.java)
                "ChatBot"-> Intent(this, ChatbotActivity::class.java)
                else -> null
            }
            intent?.let {
                it.putExtra("intentExtraIdPersonaje", idPersonaje)
                tts.stop()
                startActivity(it)
            }
        }
    }

    private fun randomEncounter(): String {
        val encounters = arrayOf("Objeto", "Ciudad", "Mercader", "Enemigo", "Interactuar", "ChatBot",)
        return encounters[Random.nextInt(encounters.size)]
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val idioma = tts!!.setLanguage(Locale.ITALIAN)
            if (idioma == TextToSpeech.LANG_MISSING_DATA
                || idioma == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Log.e("TTS Error", "Lenguaje no admitido")
            } else {

                Handler(Looper.getMainLooper()).postDelayed({
                    Handler(Looper.getMainLooper()).postDelayed({
                        speak(Botontirar.text?.toString())
                    },2000)
                    Handler(Looper.getMainLooper()).postDelayed({
                        speak(Infotext.text?.toString())
                    },4000)
                },1000)

            }
        } else {
            Log.e("TTS Error","Error al cargar")
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
        tts!!.stop()
        tts!!.shutdown()
    }
    @Suppress("DEPRECATION")
    private fun speak(ttsText: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(ttsText, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            tts!!.speak(ttsText, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

}



