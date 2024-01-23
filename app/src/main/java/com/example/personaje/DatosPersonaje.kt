package com.example.personaje

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DatosPersonaje : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_informacion)


        val botonVolver: Button = findViewById(R.id.botonVolver)

        botonVolver.setOnClickListener {
            // Crear Intent para ir a MainActivity
            val intent = Intent(this, Inicio::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Iniciar la actividad
            startActivity(intent)
        }

        // Obtener el objeto Personaje del Intent
        val personaje = intent.getParcelableExtra<Personaje>("personaje")

        // Verificar si el objeto Personaje no es nulo
        if (personaje != null) {
            // Mostrar los valores en TextViews
            val nombreTextView: TextView = findViewById(R.id.nombreTextView)
            val razaTextView: TextView = findViewById(R.id.razaTextView)
            val estadoVitalTextView: TextView = findViewById(R.id.estadoVitalTextView)
            val pesoMochilaTextView: TextView = findViewById(R.id.pesoMochilaTextView)

            nombreTextView.text = "Nombre: ${personaje.getNombre()}"
            razaTextView.text = "Raza: ${personaje.getRaza()}"
            estadoVitalTextView.text = "Estado Vital: ${personaje.getEstadoVital()}"
            pesoMochilaTextView.text = "Peso de Mochila: ${personaje.getPesoMochila()}"
        }

        val botonComenzarAventura: Button = findViewById(R.id.botonComenzarAventura)
        botonComenzarAventura.setOnClickListener {
            // Crear Intent y pasar a pantalla del dado
            val intent = Intent(this, PantallaDado::class.java)
            startActivity(intent)
        }
    }
}