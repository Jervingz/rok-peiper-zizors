package com.escudero.rokpeiperzizors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResumen: TextView
    private lateinit var btnJugar: Button
    private var ultimoMarcador: String = "Aún no has jugado"

    private val launchJuego = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val ganadas = data?.getIntExtra("ganadas", 0) ?: 0
            val perdidas = data?.getIntExtra("perdidas", 0) ?: 0
            val empatadas = data?.getIntExtra("empatadas", 0) ?: 0
            ultimoMarcador = "🟢 Ganaste $ganadas | 🔴 Perdiste $perdidas | 🟡 Empataste $empatadas"
            tvResumen.text = ultimoMarcador
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResumen = findViewById(R.id.tvResumen)
        btnJugar = findViewById(R.id.btnJugar)
        tvResumen.text = ultimoMarcador

        btnJugar.setOnClickListener {
            val intent = Intent(this, JuegoActivity::class.java)
            launchJuego.launch(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}