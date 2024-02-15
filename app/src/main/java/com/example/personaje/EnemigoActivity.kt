package com.example.personaje



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class EnemigoActivity : AppCompatActivity() {

    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_enemigo)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)

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
}