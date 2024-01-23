package com.example.personaje



import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class EnemigoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_enemigo)

        val LucharButton = findViewById<Button>(R.id.Botonluchar)
        LucharButton.setOnClickListener {
            finish()
        }

        val HuirButton = findViewById<Button>(R.id.Botonhuir)
        HuirButton.setOnClickListener {
            finish()
        }
    }
}