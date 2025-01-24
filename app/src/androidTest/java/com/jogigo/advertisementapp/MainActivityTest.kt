package com.jogigo.advertisementapp


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.jogigo.advertisementapp.features.ui.screens.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*


class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testActivityLaunches() {
        activityRule.scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }

    @Test
    fun testIfViewPagerIsDisplayed() {
        onView(withId(R.id.viewpager)).check(matches(isDisplayed()))
    }
}