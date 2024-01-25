package com.example.personaje

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ObjetoActivity : AppCompatActivity() {

    private val dbHelper: DBHelper by lazy { DBHelper(this) }
    private var espacioMochila = 100 // Suponiendo que empiezas con 100 de espacio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_objeto)

        val recogerButton = findViewById<Button>(R.id.Botonrecoger)
        recogerButton.setOnClickListener {
            val articulo = dbHelper.obtenerObjetoAleatorio()
            articulo?.let {
                if (espacioMochila >= it.getPeso()) {
                    espacioMochila -= it.getPeso()
                    val mensaje = "¡Enhorabuena! Has encontrado: ${it.getNombre()}, Tipo: ${it.getTipoArticulo()}, Peso: ${it.getPeso()}, Precio: ${it.getPrecio()}"
                    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "No hay suficiente espacio en la mochila", Toast.LENGTH_SHORT).show()
                }
            }
        }


        val continuarButton = findViewById<Button>(R.id.Botoncontobjeto)
        continuarButton.setOnClickListener {
            finish()
        }
    }
}
