package com.example.personaje
/*
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ObjetoActivity : AppCompatActivity() {

    private lateinit var botonRecoger: Button
    private lateinit var botonContinuar: Button
    private lateinit var imagenObjeto: ImageView
    private lateinit var dbGeneral: BaseDeDatosGeneral

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_objeto)

        dbGeneral = BaseDeDatosGeneral(this)
        botonRecoger = findViewById(R.id.Botonrecoger)
        botonContinuar = findViewById(R.id.Botoncontobjeto)
        imagenObjeto = findViewById(R.id.Objeto)

        botonRecoger.setOnClickListener {
            recogerObjeto()
        }

        botonContinuar.setOnClickListener {
            finish()
        }
    }

    private fun obtenerIdImagenPorNumero(numero: Int): Int {
        return when (numero) {
            1 -> R.drawable.moneda
            2 -> R.drawable.espada
            3 -> R.drawable.martillo
            4 -> R.drawable.objeto
            5 -> R.drawable.baston
            6 -> R.drawable.pocion
            7 -> R.drawable.ira
            8 -> R.drawable.escudo
            9 -> R.drawable.armadura
            10 -> R.drawable.daga
            else -> R.drawable.cofre
        }
    }

    @SuppressLint("SetTextI18n", "Range")
    private fun recogerObjeto() {
        val cursor = dbGeneral.obtenerArticuloAleatorio()
        if (cursor != null && cursor.moveToFirst()) {

            val nombre = cursor.getString(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_NOMBRE))
            val peso = cursor.getInt(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_PESO_ARTICULO))
            val idImagen = cursor.getInt(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_DRAWABLE))
            val resourceId = obtenerIdImagenPorNumero(idImagen)

            cursor.close()

            val espacioDisponible = dbGeneral.obtenerEspacioDisponibleMochila(1)

            if (peso <= espacioDisponible) {

                dbGeneral.actualizarEspacioMochila(1, espacioDisponible - peso)

                val inflater = layoutInflater
                val layout = inflater.inflate(R.layout.toast_layout, null)
                val image = layout.findViewById<ImageView>(R.id.toast_image)
                val text = layout.findViewById<TextView>(R.id.toast_text)

                imagenObjeto.setImageResource(resourceId)
                Toast.makeText(this, "Has recogido $nombre. Espacio restante en la mochila: ${espacioDisponible - peso}", Toast.LENGTH_LONG).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 2000)
            } else {
                Toast.makeText(this, "No hay suficiente espacio en la mochila para recoger $nombre.", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, "No se pudo recoger un objeto.", Toast.LENGTH_SHORT).show()
        }
    }
}


 */
