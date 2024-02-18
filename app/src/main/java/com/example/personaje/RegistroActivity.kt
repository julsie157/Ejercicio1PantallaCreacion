package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var backButton: Button
    private lateinit var signInButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registro)
        auth = Firebase.auth
        backButton = findViewById(R.id.botonAtrasReggistro)
        signInButton = findViewById(R.id.buttonConfirmar)
        emailEditText = findViewById(R.id.editTextEmailRegister)
        passwordEditText = findViewById(R.id.editTextPasswordRegister)

        backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()


            if (email.isBlank() || password.isBlank()) {
                showToast("El mail o la contraseña no puede estar vacío.")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        showToast("Registro Correcto!")

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        if (password.length<6){ showToast("Error de autentificación: Contraseña Demasiado Corta ") }
                        else{ showToast("Error de autentificación: El E-mail ya esta en Uso ") }
                    }
                }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}