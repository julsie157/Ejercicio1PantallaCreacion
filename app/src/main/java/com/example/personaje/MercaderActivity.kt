package com.example.personaje

import android.content.Intent
import android.os.Bundle
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
    private lateinit var textviewOroEspacio: TextView
    private lateinit var botonComprar: Button
    private lateinit var botonVender: Button
    private lateinit var botonCancelar: Button
    private lateinit var botonContinuar: Button


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
        textviewOroEspacio = findViewById(R.id.textviewOroEspacio)

        val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)

        actualizarOroEspacio(idMochila)

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
            val intent = Intent(this, PantallaDado::class.java)
            intent.putExtra("intentExtraIdPersonaje", idPersonaje)
            startActivity(intent)
            finish()
        }
    }

    private fun mostrarPanelComercio() {
        panelComercio.visibility = View.VISIBLE
    }

    private fun mostrarArticulosParaCompra() {
        panelArticulos.removeAllViews()
        val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        val listadoArticulos = dbGeneral.obtenerArticulosAleatoriosDelMercader(idMochila)

        val oroActual = dbGeneral.obtenerOroMochila(idMochila)

        panelArticulos.visibility = View.VISIBLE

        listadoArticulos.forEach { articulo ->
            val view = layoutInflater.inflate(R.layout.layout_aux, panelArticulos, false)
            setupArticuloView(view, articulo, oroActual, idMochila, true)
            panelArticulos.addView(view)
        }
    }

    private fun mostrarArticulosParaVenta() {
        panelArticulos.removeAllViews()
        val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        val listadoArticulos = dbGeneral.obtenerArticulosPorIdMochila(idMochila)

        if (listadoArticulos.isEmpty()) {
            Toast.makeText(this, "La mochila esta vacia.", Toast.LENGTH_SHORT).show()
            panelArticulos.visibility = View.GONE
            return
        }
        panelArticulos.visibility = View.VISIBLE

        listadoArticulos.forEach { articulo ->
            val view = layoutInflater.inflate(R.layout.layout_aux, panelArticulos, false)
            setupArticuloView(view, articulo, dbGeneral.obtenerOroMochila(idMochila), idMochila, false)
            panelArticulos.addView(view)
        }
    }

    private fun setupArticuloView(view: View, articulo: Articulo, oroActual: Int, idMochila: Int, esCompra: Boolean) {
        val nombreArticulo = view.findViewById<TextView>(R.id.nombreArticulo)
        val precioArticulo = view.findViewById<TextView>(R.id.precioArticulo)
        val pesoArticulo = view.findViewById<TextView>(R.id.pesoArticulo)
        val imagenArticulo = view.findViewById<ImageView>(R.id.imageArticulo)
        val botonAccion = view.findViewById<Button>(R.id.botonConfirmarAux)

        nombreArticulo.text = articulo.getNombre().name
        precioArticulo.text = "Precio: ${articulo.getPrecio()}"
        pesoArticulo.text = "Peso: ${articulo.getPeso()}"
        imagenArticulo.setImageResource(obtenerIdImagenPorNumero(articulo.getImagenId()))

        botonAccion.text = if (esCompra) "Comprar" else "Vender"

        botonAccion.setOnClickListener {
            if (esCompra) {
                comprarArticulo(articulo, idMochila, oroActual)
            } else {
                venderArticulo(articulo, idMochila)
            }
        }
    }

    private fun comprarArticulo(articulo: Articulo, idMochila: Int, oroActual: Int) {
        val precio = articulo.getPrecio()
        val peso = articulo.getPeso()

        if (oroActual >= precio && dbGeneral.obtenerEspacioDisponibleMochila(idMochila) >= peso) {
            dbGeneral.actualizarOroMochila(idMochila, oroActual - precio)
            dbGeneral.anadirArticuloAMochila(idMochila, articulo.getIdArticulo())
            dbGeneral.actualizarEspacioMochila(idMochila, peso)
            actualizarOroEspacio(idMochila)
            Toast.makeText(this, "Artículo comprado: ${articulo.getNombre().name}", Toast.LENGTH_SHORT).show()
            mostrarArticulosParaCompra()
        } else {
            Toast.makeText(this, "No puedes comprar este artículo.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun venderArticulo(articulo: Articulo, idMochila: Int) {
        val precio = articulo.getPrecio()
        dbGeneral.actualizarOroMochila(idMochila, dbGeneral.obtenerOroMochila(idMochila) + precio)
        dbGeneral.eliminarArticuloDeMochila(idMochila, articulo.getIdInventario())
        dbGeneral.actualizarEspacioMochila(idMochila, -articulo.getPeso())
        actualizarOroEspacio(idMochila)
        Toast.makeText(this, "Artículo vendido: ${articulo.getNombre().name}", Toast.LENGTH_SHORT).show()
        mostrarArticulosParaVenta()
    }

    private fun actualizarOroEspacio(idMochila: Int) {
        val oroActual = dbGeneral.obtenerOroMochila(idMochila)
        val espacioDisponible = dbGeneral.obtenerEspacioDisponibleMochila(idMochila)
        textviewOroEspacio.text = "Oro: $oroActual - Espacio Disponible: $espacioDisponible"
    }

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
            11 -> R.drawable.jamon
            else -> R.drawable.cofre
        }
    }
}

