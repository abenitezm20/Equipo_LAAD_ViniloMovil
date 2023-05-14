package com.laad.viniloapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)

public class TestListarColeccionistas {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun listarColeccionistaTest() {
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

        //selecciona opcion del menu

        val menu = Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        val listCollectorSelec =
            Espresso.onView(ViewMatchers.withId(R.id.nav_collector))
        listCollectorSelec.perform(ViewActions.click());

        // Verificar lista colleciones, si existe por lo menos uno la prueba es Valida
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
    }
}