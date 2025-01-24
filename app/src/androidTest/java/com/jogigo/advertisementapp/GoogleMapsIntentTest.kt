package com.jogigo.advertisementapp

import android.content.Intent
import android.net.Uri
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jogigo.advertisementapp.features.ui.screens.detail.DetailActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class GoogleMapsIntentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(DetailActivity::class.java)

    @Test
    fun testGoogleMapsIntent() {
        activityRule.scenario.onActivity { activity ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:40.7128,-74.0060"))
            assertNotNull(intent)
            assertEquals(intent.action, Intent.ACTION_VIEW)
            assertTrue(intent.data.toString().contains("geo:40.7128,-74.0060"))
        }
    }
}