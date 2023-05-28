package com.laad.viniloapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.laad.viniloapp.models.AlbumRequest
import net.datafaker.Faker
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random


@LargeTest
@RunWith(AndroidJUnit4::class)
class TestCrearAlbum {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun crearAlbumTest() {
        // Ingresar como coleccionista
        Thread.sleep(1000)
        val visitorBtn = Espresso.onView(
            Matchers.allOf(withId(R.id.collector_home_button), ViewMatchers.isDisplayed())
        )
        visitorBtn.perform(click())

        // Ingresar a la opción agregar álbum
        Thread.sleep(1000)
        val floatingActionButton = Espresso.onView(
            Matchers.allOf(withId(R.id.fab), ViewMatchers.isDisplayed())
        )
        floatingActionButton.perform(click())

        // Diligenciar información nuevo álbum
        val newAlbum = getAlbum()
        fillNewAlbum(newAlbum)

        // Guardar nuevo álbum
        Thread.sleep(1000)
        val createAlbumButton = Espresso.onView(
            Matchers.allOf(withId(R.id.create_album_button))
        )
        createAlbumButton.perform(scrollTo(), click())
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

    private fun fillNewAlbum(newAlbum: AlbumRequest) {
        //Nombre
        Thread.sleep(1000)
        val nameEditText = Espresso.onView(
            Matchers.allOf(withId(R.id.createAlbumName), ViewMatchers.isDisplayed())
        )
        nameEditText.perform(replaceText(newAlbum.name), closeSoftKeyboard())

        //URL Imagen
        Thread.sleep(1000)
        val imageEditText = Espresso.onView(
            Matchers.allOf(withId(R.id.createAlbumImage), ViewMatchers.isDisplayed())
        )
        imageEditText.perform(replaceText(newAlbum.cover), closeSoftKeyboard())

        //Fecha lanzamiento
        Thread.sleep(1000)
        val releaseTextView = Espresso.onView(
            Matchers.allOf(withId(R.id.releaseDate), ViewMatchers.isDisplayed())
        )
        releaseTextView.perform(scrollTo(), click())
        val materialButton = Espresso.onView(
            Matchers.allOf(withId(android.R.id.button1), withText("OK"))
        )
        materialButton.perform(scrollTo(), click())

        //Descripción
        Thread.sleep(1000)
        val descEditText = Espresso.onView(
            Matchers.allOf(
                withId(R.id.createAlbumDesc), ViewMatchers.isDisplayed()
            )
        )
        descEditText.perform(replaceText(newAlbum.description), closeSoftKeyboard())

        //Genero
        Thread.sleep(1000)
        val genreSpinner = Espresso.onView(
            Matchers.allOf(
                withId(R.id.musicGenreSpinner), ViewMatchers.isDisplayed()
            )
        )
        genreSpinner.perform(click())
        onData(Matchers.allOf(`is`(instanceOf(String::class.java)), `is`(newAlbum.genre))).perform(
            click()
        )

        //Casa discografica
        Thread.sleep(1000)
        val recordLabelSpinner = Espresso.onView(
            Matchers.allOf(
                withId(R.id.recordLabelSpinner), ViewMatchers.isDisplayed()
            )
        )
        recordLabelSpinner.perform(click())
        onData(
            Matchers.allOf(
                `is`(instanceOf(String::class.java)), `is`(newAlbum.recordLabel)
            )
        ).perform(
            click()
        )
    }

}