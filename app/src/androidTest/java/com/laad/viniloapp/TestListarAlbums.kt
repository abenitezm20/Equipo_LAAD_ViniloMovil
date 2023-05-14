package com.laad.viniloapp

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Test
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

@LargeTest
@RunWith(AndroidJUnit4::class)

public class TestListarAlbums {
    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun coleccionistaTest() {
        print("Inicio Test")

        Thread.sleep(1000)
        val collectorBtn =
            onView(allOf(withId(R.id.collector_home_button), ViewMatchers.isDisplayed()))
        collectorBtn.perform(click())

        val menu = onView(allOf(withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        val roleAppText = onView(allOf(withId(R.id.textViewAppRole)))
        roleAppText.check(ViewAssertions.matches(ViewMatchers.withText(R.string.nav_view_role_collector)))
    }

    @Test
    fun listarAlbumesTest() {
        print("Inicio Test")

        Thread.sleep(1000)
        val collectorBtn =
            onView(allOf(withId(R.id.visitor_home_button), ViewMatchers.isDisplayed()))
        collectorBtn.perform(click())

        val menu = onView(allOf(withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        //selecciona opcion del menu
        val listAlbumSelec =
            onView(withId(R.id.nav_albums))
        listAlbumSelec.perform(click());

        // Verificar lista albumes, si existe por lo menos uno la prueba es Valida
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
    }
}