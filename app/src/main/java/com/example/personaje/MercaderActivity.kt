package com.example.personaje

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class MercaderActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mercaderlayout)

        val botonContinuarmercader: Button = findViewById(R.id.botonContinuarmercader)

        botonContinuarmercader.setOnClickListener {
            // Crear Intent y pasar a Maincontinuarmercader sin datos adicionales
            val intent = Intent(this, Maincontinuarmercader::class.java)
            startActivity(intent)
        }
    }
}