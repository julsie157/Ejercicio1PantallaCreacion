package com.example.personaje


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GuaridaActivity : AppCompatActivity() {

    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_guarida)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)

        val botonSalir: Button = findViewById(R.id.BotonSalirGuarida)
        botonSalir.setOnClickListener {

            val intent = Intent(this, PantallaDado::class.java)
            startActivity(intent)
            finish()
        }

        val botonEntrar: Button = findViewById(R.id.BotonEntrarGuarida)
        botonEntrar.setOnClickListener {

            val intent = Intent(this, ReclutarMascotaActivity::class.java)
            intent.putExtra("intentExtraIdPersonaje", idPersonaje)
            startActivity(intent)
        }
    }
}
