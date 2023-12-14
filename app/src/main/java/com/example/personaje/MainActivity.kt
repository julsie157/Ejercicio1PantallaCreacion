package com.example.personaje

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerRaza = findViewById<Spinner>(R.id.spinnerRaza)
        val opcionesRaza = arrayOf("Humano","Elfo","Maldito","Enano")
        spinnerRaza.adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,opcionesRaza)

    }


}