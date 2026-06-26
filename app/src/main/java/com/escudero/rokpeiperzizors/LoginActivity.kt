package com.escudero.rokpeiperzizors

import android.content.Intent
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

class LoginActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Room init
        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        // Referencias UI
        val tilEmail = findViewById<TextInputLayout>(R.id.tilCorreo)
        val etEmail = findViewById<TextInputEditText>(R.id.etCorreo)

        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)

        val btnLogin = findViewById<MaterialButton>(R.id.btnIngresar)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro) // ✅ Agregar referencia

        // ✅ Ir a Registro
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        // Botón login
        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (!validateInputs(tilEmail, email, tilPassword, password)) {
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = userDao.login(email, password)

                if (user != null) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Bienvenido ${user.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Credenciales incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validateInputs(
        tilEmail: TextInputLayout,
        email: String,
        tilPassword: TextInputLayout,
        password: String
    ): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            tilEmail.error = "El correo es obligatorio"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Correo inválido"
            isValid = false
        } else {
            tilEmail.error = null
        }

        if (password.isEmpty()) {
            tilPassword.error = "La contraseña es obligatoria"
            isValid = false
        } else if (password.length < 6) {
            tilPassword.error = "Mínimo 6 caracteres"
            isValid = false
        } else {
            tilPassword.error = null
        }

        return isValid
    }
}