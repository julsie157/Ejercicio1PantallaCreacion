package com.example.personaje

import android.content.Intent
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Random

class CiudadActivity : AppCompatActivity() {

    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_ciudad)



        mediaPlayer = MediaPlayer.create(this, R.raw.fayenza)
        mediaPlayer.setVolume(0.1f, 0.1f)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje",-1L)

        val volverButton = findViewById<Button>(R.id.BotonVolverCiudad)
        volverButton.setOnClickListener {
            val intent = Intent(this, PantallaDado::class.java)
            intent.putExtra("intentExtraIdPersonaje", idPersonaje)
            startActivity(intent)
            finish()
        }

        FetchProvinciasTask().execute()
    }

    inner class FetchProvinciasTask : AsyncTask<Void, Void, String>() {

        private val requestUrl = "https://www.el-tiempo.net/api/json/v2/provincias"

        override fun doInBackground(vararg params: Void?): String? {
            try {
                val url = URL(requestUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val stringBuilder = StringBuilder()

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }

                return stringBuilder.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            result?.let {
                try {
                    val jsonObj = JSONObject(it)
                    val provincias = jsonObj.getJSONArray("provincias")

                    if (provincias.length() > 0) {
                        val index = Random().nextInt(provincias.length())
                        val provincia = provincias.getJSONObject(index)

                        val nombreProvincia = provincia.getString("NOMBRE_PROVINCIA")
                        val textViewProvincia = findViewById<TextView>(R.id.textViewProvincia)
                        textViewProvincia.text = "Vas a entrar en $nombreProvincia"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        mediaPlayer.start()
    }
    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
    }
}

