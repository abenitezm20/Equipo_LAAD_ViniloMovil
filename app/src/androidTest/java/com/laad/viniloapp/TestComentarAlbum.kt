package com.laad.viniloapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import net.datafaker.Faker
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestComentarAlbum {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun comentarAlbumTest() {
        // Ingresar como coleccionista
        Thread.sleep(1000)
        val visitorBtn = Espresso.onView(
            Matchers.allOf(ViewMatchers.withId(R.id.collector_home_button), ViewMatchers.isDisplayed())
        )
        visitorBtn.perform(ViewActions.click())

        // Verificar lista álbumes e ingresar a la primera posición
        Thread.sleep(500)
        val recyclerView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumsRv),
                ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.nav_host_fragment_content_home))),
                ViewMatchers.isDisplayed()
            )
        )
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, ViewActions.click()
            )
        )

        //acceder a comentar el album seleccionado
        Thread.sleep(1000)
        val commentBtn = Espresso.onView(
            Matchers.allOf(ViewMatchers.withId(R.id.comment_album_button))
        )
        commentBtn.perform(ViewActions.scrollTo(), ViewActions.click())

        // Diligenciar información nuevo comentario
        fillNewComment()

        // Guardar nuevo comentario
        Thread.sleep(1000)
        val createCommentButton = Espresso.onView(
            Matchers.allOf(ViewMatchers.withId(R.id.save_comment_button))
        )
        createCommentButton.perform(ViewActions.click())
    }

    private fun fillNewComment(){
        val faker = Faker()

        //Descripcion
        Thread.sleep(1000)
        var desc = faker.name().fullName()
        val nameDescText = Espresso.onView(
            Matchers.allOf(ViewMatchers.withId(R.id.descCommentAlbum), ViewMatchers.isDisplayed())
        )
        nameDescText.perform(
            ViewActions.replaceText(desc),
            ViewActions.closeSoftKeyboard()
        )

        //Calificacion
        Thread.sleep(1000)
        val calificaSpinner = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.calificaSpinner), ViewMatchers.isDisplayed()
            )
        )
        calificaSpinner.perform(ViewActions.click())
        Espresso.onData(
            Matchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("4")
            )
        ).perform(
            ViewActions.click()
        )
    }
}