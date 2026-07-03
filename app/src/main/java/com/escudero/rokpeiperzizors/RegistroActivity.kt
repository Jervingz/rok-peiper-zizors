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

    private var usuarioActual: Usuario? = null  // 👈 EDIT MODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        val tilNombre = findViewById<TextInputLayout>(R.id.tilNombre)
        val etNombre = findViewById<TextInputEditText>(R.id.etNombre)

        val tilEmail = findViewById<TextInputLayout>(R.id.tilCorreo)
        val etEmail = findViewById<TextInputEditText>(R.id.etCorreo)

        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)

        val tilConfirm = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val etConfirm = findViewById<TextInputEditText>(R.id.etConfirmPassword)

        val btn = findViewById<MaterialButton>(R.id.btnRegistrar)

        // 🔥 RECIBIR USUARIO (EDIT MODE)
        usuarioActual = intent.getSerializableExtra("usuario") as? Usuario

        // 🔥 SI VIENE USUARIO → CARGAR DATOS
        usuarioActual?.let {
            etNombre.setText(it.nombre)
            etEmail.setText(it.email)
            etPassword.setText(it.password)
            etConfirm.setText(it.password)
        }

        btn.setOnClickListener {

            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirm = etConfirm.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || password != confirm) {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                // 🔥 SI EXISTE USUARIO → UPDATE
                if (usuarioActual != null) {

                    val actualizado = usuarioActual!!.copy(
                        nombre = nombre,
                        email = email,
                        password = password
                    )

                    userDao.updateUser(actualizado)

                    Toast.makeText(this@RegistroActivity, "Usuario actualizado", Toast.LENGTH_SHORT).show()

                } else {

                    // 🔥 CREATE
                    val nuevo = Usuario(
                        nombre = nombre,
                        email = email,
                        password = password
                    )

                    userDao.insertUser(nuevo)

                    Toast.makeText(this@RegistroActivity, "Usuario creado", Toast.LENGTH_SHORT).show()
                }

                finish()
            }
        }
    }
}