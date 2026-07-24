package com.escudero.rokpeiperzizors

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginUITest {


    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)


    @Test
    fun loginCamposVacios_muestraErrorCorreo() {


        // ARRANGE
        // La pantalla LoginActivity ya se abre automáticamente


        // ACT
        // Presiona el botón sin llenar datos

        onView(withId(R.id.btnIngresar))
            .perform(click())


        // ASSERT
        // Verifica que aparece el mensaje de error

        onView(withText("El correo es obligatorio"))
            .check(matches(isDisplayed()))

    }
}