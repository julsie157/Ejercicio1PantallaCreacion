package com.example.personaje

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    // Declaración de variables
    private lateinit var registerView: TextView
    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var dbGeneral: BaseDeDatosGeneral // Asegúrate de inicializarlo

    // Declaración de Instancia de autenticación de Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        dbGeneral = BaseDeDatosGeneral(this)

        // Inicialización de Instancia de autenticación de Firebase
        auth = FirebaseAuth.getInstance()

        // Asignación de elementos del XML a las variables
        registerView = findViewById(R.id.textViewRegister)
        loginButton = findViewById(R.id.buttonLogin)
        emailEditText = findViewById(R.id.editTextEmailLogin)
        passwordEditText = findViewById(R.id.editTextPassLogin)


        // Evento de escucha de pulsado de botón de registro
        registerView.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        // Evento de escucha de pulsado de botón de inicio de sesión
        loginButton.setOnClickListener {
            //val email = emailEditText.text.toString().trim()
           // val password = passwordEditText.text.toString().trim()

            val email = "paco@gmail.com"
            val password = "123456A"

            // Comprobación campos en blanco
            if (email.isBlank() || password.isBlank()) {
                showToast("El mail o la contraseña no pueden estar vacíos.")
                return@setOnClickListener
            }

            // Inicio de sesión de Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val personaje: Personaje? = dbGeneral.obtenerPersonajePorEmail(email)
                        showToast("Inicio de sesión exitoso!")
                        var intent = Intent(this, Inicio::class.java).apply {
                            putExtra("intentExtraEmail", email)
                        }
                        // && personaje.isVivo() == true
                        if (personaje!=null){
                            intent = Intent(this, PantallaDado::class.java).apply{
                                putExtra("intentExtraIdPersonaje", personaje.getId())
                            }
                        }

                        startActivity(intent)
                        finish()

                    } else {
                        showToast("Error de autentificación: E-mail/Contraseña NO VALIDA")
                    }
                }

        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}