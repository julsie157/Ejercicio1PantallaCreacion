package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity



class Inicio : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: Button
    private lateinit var seekBar: SeekBar
    private lateinit var spinnerRaza: Spinner
    private lateinit var spinnerClase: Spinner
    private lateinit var spinnerEstadoVital: Spinner
    private lateinit var nombreEditText: EditText
    private lateinit var continuarButton: Button
    private lateinit var imagen: ImageView

    private val imagenes = mutableMapOf<String, Int>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_inicio)

        dbGeneral = BaseDeDatosGeneral(this)

        //para musica
        mediaPlayer = MediaPlayer.create(this, R.raw.temita)
        MusicPlayer.init(this)
        playButton = findViewById<Button>(R.id.play_button)
        seekBar = findViewById<SeekBar>(R.id.seekBar)

        MusicPlayer.init(this)

        updatePlayButton()

        playButton.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            } else {
                mediaPlayer?.start()
                startSeekBarUpdate()
            }
            updatePlayButton()
        }



        spinnerRaza = findViewById(R.id.spinnerRaza)
        spinnerClase = findViewById(R.id.spinnerClase)
        spinnerEstadoVital = findViewById(R.id.spinnerEstadoVital)
        nombreEditText = findViewById(R.id.Nombre)
        continuarButton = findViewById(R.id.Continuar)
        imagen = findViewById(R.id.Imagen)

        setupSpinners()
        llenarMapaDeImagenes()

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                actualizarImagen()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerRaza.onItemSelectedListener = spinnerListener
        spinnerClase.onItemSelectedListener = spinnerListener
        spinnerEstadoVital.onItemSelectedListener = spinnerListener

        continuarButton.setOnClickListener {

            val email = intent.getStringExtra("intentExtraEmail")
            val nombre = nombreEditText.text.toString()
            val raza = spinnerRaza.selectedItem.toString()
            val clase = spinnerClase.selectedItem.toString()
            val estadoVital = spinnerEstadoVital.selectedItem.toString()
            val selectedRaza = spinnerRaza.selectedItem.toString()
            val selectedClase = spinnerClase.selectedItem.toString()
            val selectedEstado = spinnerEstadoVital.selectedItem.toString()
            val key = "$selectedRaza, $selectedClase, $selectedEstado"
            val imagenId = imagenes[key] ?: R.drawable.gnomopocho




            val intent = Intent(this, DatosPersonaje::class.java).apply {
                putExtra("intentExtraEmail", email)
                putExtra("intentExtraNombre", nombre)
                putExtra("intentExtraRaza", raza)
                putExtra("intentExtraClase", clase)
                putExtra("intentExtraEstadoVital", estadoVital)
                putExtra("intentExtraImagenId", imagenId)
            }
            startActivity(intent)


        }

    }

    private fun actualizarImagen() {
        val selectedRaza = spinnerRaza.selectedItem.toString()
        val selectedClase = spinnerClase.selectedItem.toString()
        val selectedEstado = spinnerEstadoVital.selectedItem.toString()

        val key = "$selectedRaza, $selectedClase, $selectedEstado"
        val imagenId = imagenes[key]
        imagen.setImageResource(imagenId ?: R.drawable.gnomopocho)
    }

    private fun llenarMapaDeImagenes() {
        // Enanos
        imagenes["Enano, Mago, Joven"] = R.drawable.magoenanojoven
        imagenes["Enano, Mago, Adulto"] = R.drawable.magoenanoadulto
        imagenes["Enano, Mago, Anciano"] = R.drawable.magoenanoanciano

        imagenes["Enano, Brujo, Joven"] = R.drawable.brujoenanojoven
        imagenes["Enano, Brujo, Adulto"] = R.drawable.brujoenanoadulto
        imagenes["Enano, Brujo, Anciano"] = R.drawable.brujoenanoanciano

        imagenes["Enano, Guerrero, Joven"] = R.drawable.guerreroenanojoven
        imagenes["Enano, Guerrero, Adulto"] = R.drawable.guerreroenanoadulto
        imagenes["Enano, Guerrero, Anciano"] = R.drawable.guerreroenanoanciano

        //Humanos
        imagenes["Humano, Mago, Joven"] = R.drawable.magohumanojoven
        imagenes["Humano, Mago, Adulto"] = R.drawable.magohumanoadulto
        imagenes["Humano, Mago, Anciano"] = R.drawable.magohumanoanciano

        imagenes["Humano, Brujo, Joven"] = R.drawable.brujohumanojoven
        imagenes["Humano, Brujo, Adulto"] = R.drawable.brujohumanoadulto
        imagenes["Humano, Brujo, Anciano"] = R.drawable.brujohumanoanciano

        imagenes["Humano, Guerrero, Joven"] = R.drawable.guerrerohumanojoven
        imagenes["Humano, Guerrero, Adulto"] = R.drawable.guerrerohumanoadulto
        imagenes["Humano, Guerrero, Anciano"] = R.drawable.guerrerohumanoanciano

        //Elfos
        imagenes["Elfo, Mago, Joven"] = R.drawable.magoelfojoven
        imagenes["Elfo, Mago, Adulto"] = R.drawable.magoelfoadulto
        imagenes["Elfo, Mago, Anciano"] = R.drawable.magoelfoanciano

        imagenes["Elfo, Brujo, Joven"] = R.drawable.brujoelfojoven
        imagenes["Elfo, Brujo, Adulto"] = R.drawable.brujoelfoadulto
        imagenes["Elfo, Brujo, Anciano"] = R.drawable.brujoelfoanciano

        imagenes["Elfo, Guerrero, Joven"] = R.drawable.guerreroelfojoven
        imagenes["Elfo, Guerrero, Adulto"] = R.drawable.guerreroelfoadulto
        imagenes["Elfo, Guerrero, Anciano"] = R.drawable.guerreroelfoanciano

        //Malditos
        imagenes["Maldito, Mago, Joven"] = R.drawable.magomalditojoven
        imagenes["Maldito, Mago, Adulto"] = R.drawable.magomalditoanciano
        imagenes["Maldito, Mago, Anciano"] = R.drawable.magomalditoanciano

        imagenes["Maldito, Brujo, Joven"] = R.drawable.brujomalditojoven
        imagenes["Maldito, Brujo, Adulto"] = R.drawable.brujomalditoadulto
        imagenes["Maldito, Brujo, Anciano"] = R.drawable.brujomalditoanciano

        imagenes["Maldito, Guerrero, Joven"] = R.drawable.guerreromalditojoven
        imagenes["Maldito, Guerrero, Adulto"] = R.drawable.guerreromalditoadulto
        imagenes["Maldito, Guerrero, Anciano"] = R.drawable.guerreromalditoanciano

    }

    private fun setupSpinners() {

        ArrayAdapter.createFromResource(
            this,
            R.array.razas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRaza.adapter = adapter
        }


        ArrayAdapter.createFromResource(
            this,
            R.array.clases_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerClase.adapter = adapter
        }


        ArrayAdapter.createFromResource(
            this,
            R.array.estados_vitales_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEstadoVital.adapter = adapter
        }
    }

    private fun startSeekBarUpdate() {
        seekBar.max = mediaPlayer?.duration ?: 0
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    seekBar.progress = mediaPlayer!!.currentPosition
                }
                handler.postDelayed(this, 1000)
            }
        }, 0)
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
