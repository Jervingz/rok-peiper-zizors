package com.escudero.rokpeiperzizors

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class JuegoActivity : AppCompatActivity() {

    private lateinit var tvResultado: TextView
    private lateinit var tvMarcador: TextView
    private lateinit var tvAyuda: TextView

    // Contadores
    private var ganadas = 0
    private var perdidas = 0
    private var empatadas = 0
    private var puntosJugador = 0
    private var puntosMaquina = 0

    // MediaPlayers para sonidos
    private lateinit var mpClick: MediaPlayer
    private lateinit var mpWin: MediaPlayer
    private lateinit var mpLose: MediaPlayer
    private lateinit var mpDraw: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        // Inicializar sonidos
        mpClick = MediaPlayer.create(this, R.raw.click)
        mpWin = MediaPlayer.create(this, R.raw.win)
        mpLose = MediaPlayer.create(this, R.raw.lose)
        mpDraw = MediaPlayer.create(this, R.raw.draw)

        tvResultado = findViewById(R.id.tvResultado)
        tvMarcador = findViewById(R.id.tvMarcador)
        tvAyuda = findViewById(R.id.tvAyuda)

        val btnPiedra = findViewById<Button>(R.id.btnPiedra)
        val btnPapel = findViewById<Button>(R.id.btnPapel)
        val btnTijera = findViewById<Button>(R.id.btnTijera)
        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnPiedra.setOnClickListener { jugar("Piedra") }
        btnPapel.setOnClickListener { jugar("Papel") }
        btnTijera.setOnClickListener { jugar("Tijera") }

        btnReiniciar.setOnClickListener { reiniciarPartida() }
        btnVolver.setOnClickListener { volverConResultado() }

        tvAyuda.text = "👆 Toca una opción para jugar"
    }

    private fun jugar(eleccionJugador: String) {
        tvAyuda.text = ""

        val opciones = listOf("Piedra", "Papel", "Tijera")
        val eleccionMaquina = opciones[Random.nextInt(opciones.size)]

        val resultado = when {
            eleccionJugador == eleccionMaquina -> "Empate"
            eleccionJugador == "Piedra" && eleccionMaquina == "Tijera" -> "Ganaste"
            eleccionJugador == "Papel" && eleccionMaquina == "Piedra" -> "Ganaste"
            eleccionJugador == "Tijera" && eleccionMaquina == "Papel" -> "Ganaste"
            else -> "Perdiste"
        }

        when (resultado) {
            "Ganaste" -> {
                puntosJugador++
                ganadas++
            }
            "Perdiste" -> {
                puntosMaquina++
                perdidas++
            }
            "Empate" -> {
                empatadas++
            }
        }

        // Emojis
        val emojiJugador = when (eleccionJugador) {
            "Piedra" -> "🪨"
            "Papel" -> "📄"
            else -> "✂️"
        }
        val emojiMaquina = when (eleccionMaquina) {
            "Piedra" -> "🪨"
            "Papel" -> "📄"
            else -> "✂️"
        }

        tvResultado.text = """
            $emojiJugador  vs  $emojiMaquina
            
            $resultado
        """.trimIndent()

        when (resultado) {
            "Ganaste" -> tvResultado.setTextColor(Color.parseColor("#4CAF50"))
            "Perdiste" -> tvResultado.setTextColor(Color.parseColor("#F44336"))
            "Empate" -> tvResultado.setTextColor(Color.parseColor("#FFC107"))
        }

        tvMarcador.text = "Jugador $puntosJugador  -  $puntosMaquina Máquina"

        // 🔥 REPRODUCIR SONIDO (DETENIENDO TODOS ANTES)
        reproducirSonido(resultado)
    }

    // 🔥 MÉTODO PARA REPRODUCIR SONIDO DETENIENDO LOS DEMÁS
    private fun reproducirSonido(resultado: String) {
        // Detener TODOS los sonidos antes de reproducir el nuevo
        stopAllSounds()

        // Reproducir el sonido correspondiente
        when (resultado) {
            "Ganaste" -> {
                mpWin.start()
                // Opcional: reproducir también un click al seleccionar

            }
            "Perdiste" -> {
                mpLose.start()

            }
            "Empate" -> {
                mpDraw.start()

            }
        }
    }

    // 🔥 DETENER TODOS LOS SONIDOS
    private fun stopAllSounds() {
        if (::mpClick.isInitialized && mpClick.isPlaying) mpClick.stop()
        if (::mpWin.isInitialized && mpWin.isPlaying) mpWin.stop()
        if (::mpLose.isInitialized && mpLose.isPlaying) mpLose.stop()
        if (::mpDraw.isInitialized && mpDraw.isPlaying) mpDraw.stop()
    }

    private fun reiniciarPartida() {
        puntosJugador = 0
        puntosMaquina = 0
        tvMarcador.text = "Jugador 0 - 0 Máquina"
        tvResultado.text = "Elige una opción"
        tvResultado.setTextColor(Color.WHITE)
        tvAyuda.text = "👆 Toca una opción para jugar"
        // Detener sonidos al reiniciar
        stopAllSounds()
    }

    private fun volverConResultado() {
        val intent = Intent()
        intent.putExtra("ganadas", ganadas)
        intent.putExtra("perdidas", perdidas)
        intent.putExtra("empatadas", empatadas)
        setResult(RESULT_OK, intent)
        // Liberar recursos de sonido antes de salir
        releaseSounds()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        volverConResultado()
    }

    // 🔥 LIBERAR RECURSOS DE SONIDO
    private fun releaseSounds() {
        mpClick.release()
        mpWin.release()
        mpLose.release()
        mpDraw.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseSounds()
    }
}