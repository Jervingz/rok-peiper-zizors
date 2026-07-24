package com.escudero.rokpeiperzizors

object Validator {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    fun validate(email: String, password: String): Pair<Boolean, String?> {

        if (email.isEmpty()) {
            return Pair(false, "El correo es obligatorio")
        }

        if (!EMAIL_REGEX.matches(email)) {
            return Pair(false, "Correo inválido")
        }

        if (password.isEmpty()) {
            return Pair(false, "La contraseña es obligatoria")
        }

        if (password.length < 6) {
            return Pair(false, "Mínimo 6 caracteres")
        }

        return Pair(true, null)
    }

}
