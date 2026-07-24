package com.escudero.rokpeiperzizors

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidatorTest {

    @Test
    fun validate_correoInvalido_retornaFalse() {

        val resultado = Validator.validate("usuarioSinArroba", "123456")

        assertFalse(resultado.first)
        assertEquals("Correo inválido", resultado.second)
    }

    @Test
    fun validate_passwordCorta_retornaFalse() {

        val resultado = Validator.validate("usuario@test.com", "123")

        assertFalse(resultado.first)
        assertEquals("Mínimo 6 caracteres", resultado.second)
    }

    @Test
    fun validate_datosCorrectos_retornaTrue() {

        val resultado = Validator.validate("usuario@correo.com", "123456")

        assertTrue(resultado.first)
        assertNull(resultado.second)
    }

}
