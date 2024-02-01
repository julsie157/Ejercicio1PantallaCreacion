package com.example.personaje
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ObjetoActivity : AppCompatActivity() {
    private var espacioMochilaDisponible: Int = 100 // Capacidad máxima de la mochila
    val dbHelper = Objetos_Aleatorios(this)
    val db= dbHelper.writableDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_objeto)

        val recogerButton = findViewById<Button>(R.id.Botonrecoger)
        recogerButton.setOnClickListener {
            recogerObjeto()
        }

        val continuarButton = findViewById<Button>(R.id.Botoncontobjeto)
        continuarButton.setOnClickListener {
            finish()
        }
    }

    private fun recogerObjeto() {
        TODO("Not yet implemented")
    }

}