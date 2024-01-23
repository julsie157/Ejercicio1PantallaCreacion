package com.example.personaje

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
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




        val botoncomerciarmercader: Button = findViewById(R.id)
        playButton.setVisibility(View.VISIBLE)
        playButton.setOnClickListener(object : OnClickListener() {
            fun onClick(v: View?) {
                //when play is clicked show stop button and hide play button
                playButton.setVisibility(View.GONE)
                stopButton.setVisibility(View.VISIBLE)
            }
        })

    }
}