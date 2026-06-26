package com.escudero.rokpeiperzizors

import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Room init
        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        // Referencias UI
        val tilNombre = findViewById<TextInputLayout>(R.id.tilNombre)
        val etNombre = findViewById<TextInputEditText>(R.id.etNombre)

        val tilEmail = findViewById<TextInputLayout>(R.id.tilCorreo)
        val etEmail = findViewById<TextInputEditText>(R.id.etCorreo)

        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)

        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)

        val btnRegistrar = findViewById<MaterialButton>(R.id.btnRegistrar)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        // Botón Registrar
        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // Validar
            if (!validateInputs(tilNombre, nombre, tilEmail, email, tilPassword, password, tilConfirmPassword, confirmPassword)) {
                return@setOnClickListener
            }

            // Verificar si el usuario ya existe
            lifecycleScope.launch {
                val existingUser = userDao.getUserByEmail(email)
                if (existingUser != null) {
                    Toast.makeText(
                        this@RegistroActivity,
                        "Este correo ya está registrado",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                // Crear usuario
                val nuevoUsuario = Usuario(
                    nombre = nombre,
                    correo = email,
                    password = password
                )

                try {
                    userDao.insertUser(nuevoUsuario)
                    Toast.makeText(
                        this@RegistroActivity,
                        "¡Registro exitoso! Inicia sesión",
                        Toast.LENGTH_LONG
                    ).show()
                    finish() // Volver al Login
                } catch (e: Exception) {
                    Toast.makeText(
                        this@RegistroActivity,
                        "Error al registrar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Ir a Login
        tvLogin.setOnClickListener {
            finish() // Volver al Login
        }
    }

    private fun validateInputs(
        tilNombre: TextInputLayout,
        nombre: String,
        tilEmail: TextInputLayout,
        email: String,
        tilPassword: TextInputLayout,
        password: String,
        tilConfirmPassword: TextInputLayout,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        // Validar Nombre
        if (nombre.isEmpty()) {
            tilNombre.error = "El nombre es obligatorio"
            isValid = false
        } else if (nombre.length < 3) {
            tilNombre.error = "Mínimo 3 caracteres"
            isValid = false
        } else {
            tilNombre.error = null
        }

        // Validar Email
        if (email.isEmpty()) {
            tilEmail.error = "El correo es obligatorio"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Correo inválido"
            isValid = false
        } else {
            tilEmail.error = null
        }

        // Validar Password
        if (password.isEmpty()) {
            tilPassword.error = "La contraseña es obligatoria"
            isValid = false
        } else if (password.length < 6) {
            tilPassword.error = "Mínimo 6 caracteres"
            isValid = false
        } else {
            tilPassword.error = null
        }

        // Validar Confirmar Password
        if (confirmPassword.isEmpty()) {
            tilConfirmPassword.error = "Confirma tu contraseña"
            isValid = false
        } else if (password != confirmPassword) {
            tilConfirmPassword.error = "Las contraseñas no coinciden"
            isValid = false
        } else {
            tilConfirmPassword.error = null
        }

        return isValid
    }
}