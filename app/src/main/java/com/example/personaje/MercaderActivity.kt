package com.example.personaje

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class MercaderActivity : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral
    private var idPersonaje: Long = -1L
    private lateinit var panelComercio: LinearLayout
    private lateinit var panelArticulos: LinearLayout
    private lateinit var botonComerciar: Button
    private lateinit var botonContinuar: Button
    private lateinit var botonComprar: Button
    private lateinit var botonVender: Button
    private lateinit var botonCancelar: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mercader)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)

        panelComercio = findViewById(R.id.panelComercio)
        panelArticulos = findViewById(R.id.panelArticulos)
        botonComerciar = findViewById(R.id.Botoncomerciar)
        botonComprar = findViewById(R.id.botonComprar)
        botonVender = findViewById(R.id.botonVender)
        botonCancelar = findViewById(R.id.botonCancelar)
        botonContinuar = findViewById(R.id.Botoncontmercader)


        botonComerciar.setOnClickListener {
            mostrarPanelComercio()
        }

        botonCancelar.setOnClickListener {
            panelComercio.visibility = View.GONE
        }

        botonComprar.setOnClickListener {
            mostrarArticulosParaCompra()
        }

        botonVender.setOnClickListener {
            mostrarArticulosParaVenta()
        }

        botonContinuar.setOnClickListener {
            finish()
        }


    }

    @SuppressLint("Range")
    private fun mostrarArticulosParaVenta() {

        panelArticulos.removeAllViews()

        val idMochila: Int = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        val listadoArticulos: ArrayList<Articulo> = dbGeneral.obtenerArticulosPorIdMochila(idMochila)
        val oroActual: Int = dbGeneral.obtenerOroMochila(idPersonaje)
        /*
        if (listadoArticulos.isEmpty()) {
            Toast.makeText(this, "No tienes ningún artículo para vender.", Toast.LENGTH_LONG).show()
            finish() // Finaliza la actividad actual y vuelve a la anterior
        }

         */

        panelArticulos.visibility = View.VISIBLE

        for (articulo in listadoArticulos) {
            val view = layoutInflater.inflate(R.layout.layout_aux, panelArticulos, false)
            view.findViewById<TextView>(R.id.nombreArticulo).text = articulo.getNombre().toString()
            view.findViewById<TextView>(R.id.precioArticulo).text = "Precio " + articulo.getPrecio().toString()
            view.findViewById<TextView>(R.id.pesoArticulo).text = "Peso: " + articulo.getPeso().toString()
            val recursoImagen = obtenerIdImagenPorNumero(articulo.getImagenId())
            view.findViewById<ImageView>(R.id.imageArticulo).setImageResource(recursoImagen)
            view.findViewById<Button>(R.id.botonConfirmarAux).text = "Vender"
            panelArticulos.addView(view)

            view.findViewById<Button>(R.id.botonConfirmarAux).setOnClickListener {
                //mochilageoro()

                dbGeneral.actualizarOroMochila(idPersonaje, oroActual + articulo.getPrecio())
                dbGeneral.eliminarArticuloDeMochila(idMochila, articulo.getIdInventario())
                //dbGeneral.actualizarEspacioMochilaDos(idMochila, mochila.getPeso() - articulo.getPeso())

                val pesoActualMochila = dbGeneral.obtenerEspacioDisponibleMochila(idMochila)
                dbGeneral.actualizarEspacioMochila(idMochila, pesoActualMochila - articulo.getPeso())
                Toast.makeText(
                    this@MercaderActivity,
                    "Artículo vendido exitosamente.",
                    Toast.LENGTH_SHORT
                ).show()
                mostrarArticulosParaVenta() // Actualiza la lista de artículos en venta
            }

        }

    }


    private fun mostrarPanelComercio() {
        panelComercio.visibility = View.VISIBLE
        botonComprar.visibility = View.VISIBLE
    }


    private fun mostrarArticulosParaCompra() {
        panelArticulos.removeAllViews()
        val idMochila: Int = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        val listadoArticulos: ArrayList<Articulo> = dbGeneral.obtnerArticulosAleatoriosDelMercader(idMochila)
        val oroActual: Int = dbGeneral.obtenerOroMochila(idPersonaje)
        panelArticulos.visibility = View.VISIBLE

        for (articulo in listadoArticulos) {
            val view = layoutInflater.inflate(R.layout.layout_aux, panelArticulos, false)
            view.findViewById<TextView>(R.id.nombreArticulo).text = articulo.getNombre().toString()
            view.findViewById<TextView>(R.id.precioArticulo).text =
                "Precio " + articulo.getPrecio().toString()
            view.findViewById<TextView>(R.id.pesoArticulo).text =
                "Peso: " + articulo.getPeso().toString()
            val recursoImagen = obtenerIdImagenPorNumero(articulo.getImagenId())
            view.findViewById<ImageView>(R.id.imageArticulo).setImageResource(recursoImagen)
            view.findViewById<Button>(R.id.botonConfirmarAux).text = "Comprar"
            panelArticulos.addView(view)

            view.findViewById<Button>(R.id.botonConfirmarAux).setOnClickListener {

                dbGeneral.actualizarOroMochila(idPersonaje, oroActual - articulo.getPrecio())
                dbGeneral.anadirArticuloAMochila(idMochila, articulo.getIdInventario())
                val pesoActualMochila = dbGeneral.obtenerEspacioDisponibleMochila(idMochila)
                dbGeneral.actualizarEspacioMochila(idMochila, pesoActualMochila - articulo.getPeso())
                Toast.makeText(
                    this@MercaderActivity,
                    "Artículo Comprado exitosamente.",
                    Toast.LENGTH_SHORT
                ).show()
                mostrarArticulosParaCompra()
            }

        }
    }


    /*
    @SuppressLint("Range", "MissingInflatedId")
    private fun mostrarArticulosParaCompra() {
        panelArticulos.removeAllViews()
        val cursor = dbGeneral.obtnerArticulosAleatoriosDelMercader()
        panelArticulos.visibility = View.VISIBLE
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val view = layoutInflater.inflate(R.layout.layout_aux, panelArticulos, false)
                val nombreArticulo = view.findViewById<TextView>(R.id.nombreArticulo)
                val precioArticulo = view.findViewById<TextView>(R.id.precioArticulo)
                val imagenArticulo = view.findViewById<ImageView>(R.id.imageArticulo)
                val botonConfirmar = view.findViewById<Button>(R.id.botonConfirmarAux)

                val idImagen = cursor.getInt(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_DRAWABLE))
                val recursoImagen = obtenerIdImagenPorNumero(idImagen)

                nombreArticulo.text = cursor.getString(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_NOMBRE))
                precioArticulo.text = "Precio: ${cursor.getInt(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_PRECIO))}"
                imagenArticulo.setImageResource(recursoImagen)

                botonConfirmar.setOnClickListener {
                    val precioArticulo = cursor.getInt(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_PRECIO))
                    val pesoArticulo = cursor.getInt(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_PESO_ARTICULO))
                    val idArticulo = cursor.getInt(cursor.getColumnIndex(BaseDeDatosGeneral.COLUMN_ID_ARTICULO))

                    val oroActual = dbGeneral.obtenerOroMochila(idPersonaje)
                    val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
                    val espacioDisponible = dbGeneral.obtenerEspacioDisponibleMochila(idMochila)

                    if (oroActual >= precioArticulo && espacioDisponible >= pesoArticulo) {
                        dbGeneral.actualizarOroMochila(idPersonaje, oroActual - precioArticulo)
                        dbGeneral.anadirArticuloAMochila(idMochila, idArticulo)

                        Toast.makeText(this@MercaderActivity, "Artículo comprado exitosamente.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MercaderActivity, "No tienes suficiente oro o espacio en la mochila.", Toast.LENGTH_SHORT).show()
                    }
                }
                panelArticulos.addView(view)
            } while (cursor.moveToNext())
            cursor.close()
        }
    }


     */

    private fun obtenerIdImagenPorNumero(numero: Int): Int {
        return when (numero) {
            1 -> R.drawable.moneda
            2 -> R.drawable.espada
            3 -> R.drawable.martillo
            4 -> R.drawable.garras
            5 -> R.drawable.baston
            6 -> R.drawable.pocion
            7 -> R.drawable.ira
            8 -> R.drawable.escudo
            9 -> R.drawable.armadura
            10 -> R.drawable.daga
            else -> R.drawable.cofre
        }
    }

}
