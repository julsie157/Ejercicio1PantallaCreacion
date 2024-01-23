package com.example.personaje


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


    class CiudadActivity : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.layout_ciudad)

            val entrarButton = findViewById<Button>(R.id.Botonentrar)
            entrarButton.setOnClickListener {
                finish()
            }

            val continuarButton = findViewById<Button>(R.id.Botoncontciudad)
            continuarButton.setOnClickListener {
                finish()
            }
        }
    }
