package com.laad.viniloapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class PruebaTest {

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
    fun VisitanteTest() {
        print("Inicio Test")

        Thread.sleep(1000)
        val visitorBtn =
            onView(allOf(withId(R.id.visitor_home_button), ViewMatchers.isDisplayed()))
        visitorBtn.perform(click())

        val menu = onView(allOf(withId(R.id.drawer_layout)))
        menu.perform(DrawerActions.open())
        Thread.sleep(1000)

        val roleAppText = onView(allOf(withId(R.id.textViewAppRole)))
        roleAppText.check(ViewAssertions.matches(ViewMatchers.withText(R.string.nav_view_role_visitor)))
    }

}