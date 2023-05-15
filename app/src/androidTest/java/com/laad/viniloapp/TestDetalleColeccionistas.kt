package com.laad.viniloapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class TestDetalleColecccionistas {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun coleccionistaTest() {
        print("Inicio Test")

        Thread.sleep(1000)
        val collectorBtn =
            Espresso.onView(
                Matchers.allOf(
                    ViewMatchers.withId(R.id.collector_home_button),
                    ViewMatchers.isDisplayed()
                )
            )
        collectorBtn.perform(ViewActions.click())

        val menu = Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        val roleAppText = Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.textViewAppRole)))
        roleAppText.check(ViewAssertions.matches(ViewMatchers.withText(R.string.nav_view_role_collector)))
    }

    @Test
    fun DetalleColeccionistaTest() {
        print("Inicio Test")

        Thread.sleep(1000)
        val collectorBtn =
            Espresso.onView(
                Matchers.allOf(
                    ViewMatchers.withId(R.id.visitor_home_button),
                    ViewMatchers.isDisplayed()
                )
            )
        collectorBtn.perform(ViewActions.click())

        val menu = Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        //selecciona opcion del menu
        val listAlbumSelec =
            Espresso.onView(ViewMatchers.withId(R.id.nav_collector))
        listAlbumSelec.perform(ViewActions.click());

        // Verificar lista coleccionistas, si existe por lo menos uno la prueba es Valida
        Thread.sleep(500)
        val recyclerView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.CollectorRv),
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

        // Verificar título coleccionista
        Thread.sleep(500)
        val titleTextView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.name_detail_collector), ViewMatchers.isDisplayed()
            )
        )
        titleTextView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}