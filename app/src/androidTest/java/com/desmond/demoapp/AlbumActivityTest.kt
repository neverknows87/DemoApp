package com.desmond.demoapp

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.desmond.demoapp.activity.AlbumActivity
import com.desmond.demoapp.adapter.ListAdapter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AlbumActivityTest {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(AlbumActivity::class.java)
    }

    @Test
    fun scrollToBottom() {
        onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.scrollToPosition<ListAdapter.ViewHolder>(29))
    }

    @Test
    fun scrollToItem_andClick() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ListAdapter.ViewHolder>(
                14, click()))
//
//        // Match the text in an item below the fold and check that it's displayed.
//        val itemElementText = "Imperfect Circle"
//        onView(ViewMatchers.withText(itemElementText)).check(matches(isDisplayed()))
    }

    

}