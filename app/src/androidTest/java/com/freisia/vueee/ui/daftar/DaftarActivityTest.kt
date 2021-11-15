package com.freisia.vueee.ui.daftar

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.freisia.vueee.R
import com.freisia.vueee.utils.EspressoIdlingResource
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
class DaftarActivityTest{
    @get:Rule
    var activityRule = ActivityTestRule(DaftarActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @Test
    fun loadViewPagerTest(){
        checkViewPagerItems()
        swipeLeft()
        swipeRight()
    }

    @Test
    fun loadDataMovies(){
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.list)
        val count = 20
        swipeUp(R.id.list)
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
        assertTrue(count == recyclerView.adapter?.itemCount)
    }

    @Test
    fun loadDataTV(){
        swipeLeft()
        onView(withId(R.id.list2)).check(matches(isDisplayed()))
        swipeUp(R.id.list2)
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.list2)
        val count = 20
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
        assertTrue(count == recyclerView.adapter?.itemCount)
    }

    @Test
    fun detailDataMovies(){
        val recyclerView = onView(withId(R.id.list)).check(matches(isDisplayed()))
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        val imageView = onView(withId(R.id.image)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        imageView.perform(ViewActions.swipeUp())
        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.genre)).check(matches(isDisplayed()))
        onView(withId(R.id.review)).check(matches(isDisplayed()))
        onView(withId(R.id.broadcast)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
    }

    @Test
    fun detailDataTV(){
        swipeLeft()
        sleep(1000)
        val recyclerView = onView(withId(R.id.list2)).check(matches(isDisplayed()))
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        val imageView = onView(withId(R.id.image)).check(matches(isDisplayed()))
        imageView.perform(ViewActions.swipeUp())
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.genre)).check(matches(isDisplayed()))
        onView(withId(R.id.review)).check(matches(isDisplayed()))
        onView(withId(R.id.broadcast)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
    }

    private fun checkViewPagerItems(){
        val viewPager = activityRule.activity.findViewById<ViewPager>(R.id.view_pager)
        val title = 2
        assertNotNull(viewPager)
        assertNotNull(viewPager.adapter)
        assertTrue(title == viewPager.adapter?.count)
    }

    private fun swipeLeft(){
        val viewPager = onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        viewPager.perform(ViewActions.swipeLeft())
    }

    private fun swipeRight(){
        val viewPager = onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        viewPager.perform(ViewActions.swipeRight())
    }

    private fun swipeUp(id : Int){
        val recyclerView = onView(withId(id)).check(matches(isDisplayed()))
        recyclerView.perform(ViewActions.swipeUp())
    }


}