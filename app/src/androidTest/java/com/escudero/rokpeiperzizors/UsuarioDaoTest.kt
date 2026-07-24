package com.escudero.rokpeiperzizors

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UsuarioDaoTest {


    private lateinit var database: AppDatabase
    private lateinit var dao: UserDao


    // ARRANGE GENERAL
    // Se ejecuta antes de cada prueba
    @Before
    fun createDatabase() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.userDao()
    }


    // Se ejecuta después de cada prueba
    @After
    fun closeDatabase() {

        database.close()
    }



    // PRUEBA 1:
    // Insertar usuario y leerlo

    @Test
    fun insertarUsuario_yLeerUsuario_retornaDatosCorrectos() = runBlocking {


        // ARRANGE
        val usuario = Usuario(
            nombre = "Juan Perez",
            email = "juan@gmail.com",
            password = "123456"
        )


        // ACT
        dao.insertUser(usuario)

        val resultado = dao.getUserByEmail("juan@gmail.com")


        // ASSERT

        assertEquals(
            "El nombre debe coincidir",
            "Juan Perez",
            resultado?.nombre
        )

        assertEquals(
            "El correo debe coincidir",
            "juan@gmail.com",
            resultado?.email
        )

        assertEquals(
            "La contraseña debe coincidir",
            "123456",
            resultado?.password
        )
    }



    // PRUEBA 2:
    // Login correcto e incorrecto

    @Test
    fun login_usuarioRegistrado_retornaUsuarioOCasoIncorrectoNull() = runBlocking {


        // ARRANGE

        val usuario = Usuario(
            nombre = "Maria",
            email = "maria@gmail.com",
            password = "abcdef"
        )


        dao.insertUser(usuario)



        // ACT

        val loginCorrecto = dao.login(
            "maria@gmail.com",
            "abcdef"
        )


        val loginIncorrecto = dao.login(
            "maria@gmail.com",
            "passwordMalo"
        )



        // ASSERT


        assertEquals(
            "Debe devolver el usuario correcto",
            "Maria",
            loginCorrecto?.nombre
        )


        assertNull(
            "Credenciales incorrectas deben devolver null",
            loginIncorrecto
        )

    }

}