package com.example.personaje


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ObjetoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_objeto)

        val recogerButton = findViewById<Button>(R.id.Botonrecoger)
        recogerButton.setOnClickListener {
            finish()
        }

        val continuarButton = findViewById<Button>(R.id.Botoncontobjeto)
        continuarButton.setOnClickListener {
            finish()
        }
    }
}
