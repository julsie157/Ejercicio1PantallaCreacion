package com.example.personaje



import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MercaderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mercader)

        val ComerciarButton = findViewById<Button>(R.id.Botoncomerciar)
        ComerciarButton.setOnClickListener {
            finish()
        }

        val ContinuarButton = findViewById<Button>(R.id.Botoncontmercader)
        ContinuarButton.setOnClickListener {
            finish()
        }
    }
}