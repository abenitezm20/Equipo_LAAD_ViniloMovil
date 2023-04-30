package com.laad.viniloapp

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Test
import org.hamcrest.Matchers.allOf

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
            onView(allOf(withId(R.id.collector_home_button), ViewMatchers.isDisplayed()))
        collectorBtn.perform(click())

        val menu = onView(allOf(withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        //selecciona opcion del menu
        val listAlbumSelec =
            onView(withId(R.id.nav_albums))
        listAlbumSelec.perform(click());

        //buscando el titulo de un album
        onView(allOf(withId(R.id.textView6), withText("Poeta del pueblo"))).check(matches(isDisplayed()))
    }
}