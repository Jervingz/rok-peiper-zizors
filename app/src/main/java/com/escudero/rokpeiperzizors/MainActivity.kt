package com.escudero.rokpeiperzizors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👉 Ir directamente al login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}