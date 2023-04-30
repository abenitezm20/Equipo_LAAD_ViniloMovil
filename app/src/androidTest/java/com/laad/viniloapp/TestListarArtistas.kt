package com.laad.viniloapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)

public class TestListarArtistas {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun VisitanteTest() {

        Thread.sleep(1000)
        val visitorBtn =
            Espresso.onView(
                Matchers.allOf(
                    withId(R.id.visitor_home_button),
                    ViewMatchers.isDisplayed()
                )
            )
        visitorBtn.perform(ViewActions.click())

        val menu = Espresso.onView(Matchers.allOf(withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        val roleAppText = Espresso.onView(Matchers.allOf(withId(R.id.textViewAppRole)))
        roleAppText.check(ViewAssertions.matches(ViewMatchers.withText(R.string.nav_view_role_visitor)))
    }

    @Test
    fun listarArtistasTest() {
        print("Inicio Test")

        Thread.sleep(1000)
        val visitorBtn =
            Espresso.onView(
                Matchers.allOf(
                    ViewMatchers.withId(R.id.visitor_home_button),
                    ViewMatchers.isDisplayed()
                )
            )
        visitorBtn.perform(ViewActions.click())

        val menu = Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        //selecciona opcion del menu
        val listArtistSelec =
            Espresso.onView(withId(R.id.nav_artist))
        listArtistSelec.perform(ViewActions.click());

        //buscando el titulo de un album
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.textView7),
                ViewMatchers.withText("Nombre: Rubén Blades Bellido de Luna")
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}