package com.laad.viniloapp

import android.content.Context
import android.view.View
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.laad.viniloapp.models.AlbumRequest
import net.datafaker.Faker
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random


@LargeTest
@RunWith(AndroidJUnit4::class)
class TestNegativoCrearAlbum {

    private val faker = Faker()
    private var decorView: View? = null
    private var context: Context? = null

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mActivityScenarioRule.scenario.onActivity(ActivityAction<MainActivity> { activity ->
            decorView = activity.window.decorView
        })
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun nombreInvalidoTest() {
        ingresoNuevoAlbum()
        clicNuevoAlbum()
        val nombreInvalido = context?.getString(R.string.invalid_name)
        nombreInvalido?.let { validarMensajeError(it) }
    }

    @Test
    fun sinImagenTest() {
        ingresoNuevoAlbum()
        setNombre()
        clicNuevoAlbum()
        val imagenInvalida = context?.getString(R.string.invalid_image_url)
        imagenInvalida?.let { validarMensajeError(it) }
    }

    @Test
    fun urlImagenInvalidaTest() {
        ingresoNuevoAlbum()
        setNombre()
        setImagen(faker.name().fullName())
        clicNuevoAlbum()
        val imagenInvalida = context?.getString(R.string.invalid_image_url)
        imagenInvalida?.let { validarMensajeError(it) }
    }

    @Test
    fun sinFechaLanzamientoTest() {
        ingresoNuevoAlbum()
        setNombre()
        setImagen(getAlbum().cover)
        clicNuevoAlbum()
        val fechaInvalida = context?.getString(R.string.invalid_release_date)
        fechaInvalida?.let { validarMensajeError(it) }
    }

    @Test
    fun sinDescripcionTest() {
        ingresoNuevoAlbum()
        setNombre()
        setImagen(getAlbum().cover)
        setFecha()
        clicNuevoAlbum()
        val descripcionInvalida = context?.getString(R.string.invalid_description)
        descripcionInvalida?.let { validarMensajeError(it) }
    }

    private fun ingresoNuevoAlbum() {
        // Ingresar como coleccionista
        Thread.sleep(1000)
        onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.collector_home_button), isDisplayed()
            )
        ).perform(ViewActions.click())

        // Ingresar a la opción agregar álbum
        Thread.sleep(1000)
        onView(
            Matchers.allOf(ViewMatchers.withId(R.id.fab), ViewMatchers.isDisplayed())
        ).perform(ViewActions.click())
    }

    private fun clicNuevoAlbum() {
        Thread.sleep(1000)
        onView(
            Matchers.allOf(ViewMatchers.withId(R.id.create_album_button))
        ).perform(ViewActions.scrollTo(), ViewActions.click())
    }

    private fun validarMensajeError(mensajeError: String) {
        onView(withText(mensajeError)).inRoot(
            withDecorView(
                Matchers.not(decorView)
            )
        ).check(ViewAssertions.matches(isDisplayed()));
    }

    private fun getAlbum(): AlbumRequest {
        val faker = Faker()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val recordLabels: List<String> =
            context.resources.getStringArray(R.array.record_labels).toList()
        val genres: List<String> = context.resources.getStringArray(R.array.music_genres).toList()
        return AlbumRequest(
            name = faker.name().fullName(),
            cover = "https://w7.pngwing.com/pngs/771/724/png-transparent-super-mario-bros-3-mario-kart-8-mario-angle-heroes-super-mario-bros.png",
            recordLabel = recordLabels[Random.nextInt(recordLabels.size)],
            releaseDate = faker.date().toString(),
            genre = genres[Random.nextInt(genres.size)],
            description = faker.text().text(200),
        )
    }

    private fun setNombre() {
        Thread.sleep(1000)
        onView(
            Matchers.allOf(ViewMatchers.withId(R.id.createAlbumName), isDisplayed())
        ).perform(
            ViewActions.replaceText(getAlbum().name), ViewActions.closeSoftKeyboard()
        )
    }

    private fun setImagen(url: String) {
        Thread.sleep(1000)
        onView(
            Matchers.allOf(ViewMatchers.withId(R.id.createAlbumImage), isDisplayed())
        ).perform(
            ViewActions.replaceText(url), ViewActions.closeSoftKeyboard()
        )
    }

    private fun setFecha() {
        Thread.sleep(1000)
        onView(
            Matchers.allOf(ViewMatchers.withId(R.id.releaseDate), isDisplayed())
        ).perform(ViewActions.scrollTo(), ViewActions.click())
        onView(
            Matchers.allOf(ViewMatchers.withId(android.R.id.button1), withText("OK"))
        ).perform(ViewActions.scrollTo(), ViewActions.click())
    }

}