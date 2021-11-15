package com.freisia.vueee.ui.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.freisia.vueee.R
import com.freisia.vueee.ui.favorite.FavoriteActivity
import com.freisia.vueee.utils.EspressoIdlingResource
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ListActivityTest{
    @get:Rule
    var activityRules = ActivityTestRule(FavoriteActivity::class.java)
    @get:Rule
    var activityRule = ActivityTestRule(ListActivity::class.java)

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

    @Test
    fun loadNowPlayingMovies(){
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Now Playing"))))
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.list)
        val count = 20
        swipeUp(R.id.list)
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
        assertTrue(count == recyclerView.adapter?.itemCount)
    }

    @Test
    fun loadTopRatedMovies(){
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Top Rated"))))
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.list)
        val count = 20
        swipeUp(R.id.list)
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
        assertTrue(count == recyclerView.adapter?.itemCount)
    }

    @Test
    fun loadOnAirTV(){
        swipeLeft()
        onView(withId(R.id.spinners2)).check(matches(isDisplayed()))
        onView(withId(R.id.spinners2)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.spinners2)).check(matches(withSpinnerText(containsString("On Air"))))
        onView(withId(R.id.list2)).check(matches(isDisplayed()))
        swipeUp(R.id.list2)
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.list2)
        val count = 20
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
        assertTrue(count == recyclerView.adapter?.itemCount)
    }

    @Test
    fun loadTopRatedTV(){
        swipeLeft()
        onView(allOf(withId(R.id.spinners2), isCompletelyDisplayed()))
        onView(withId(R.id.spinners2)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)),`is`("Top Rated"))).perform(click())
        onView(withId(R.id.spinners2)).check(matches(withSpinnerText(containsString("Top Rated"))))
        onView(withId(R.id.list2)).check(matches(isDisplayed()))
        swipeUp(R.id.list2)
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.list2)
        val count = 20
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
        assertTrue(count == recyclerView.adapter?.itemCount)
    }

    @Test
    fun insertDeleteFavoriteMovies(){
        val recyclerView = onView(withId(R.id.list)).check(matches(isDisplayed()))
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun insertDeleteFavoriteTV(){
        swipeLeft()
        val recyclerView = onView(withId(R.id.list2)).check(matches(isDisplayed()))
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,click()))
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun listFavoriteMovies(){
        onView(withId(R.id.favorite)).perform(click())
        val recyclerView = activityRules.activity.findViewById<RecyclerView>(R.id.list3)
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
    }

    @Test
    fun listFavoriteTV(){
        onView(withId(R.id.favorite)).perform(click())
        swipeLeftFavorite()
        val recyclerView = activityRules.activity.findViewById<RecyclerView>(R.id.list4)
        assertNotNull(recyclerView)
        assertNotNull(recyclerView.adapter)
    }

    @Test
    fun deleteAllFavoriteMovies(){
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.delete)).perform(click())
        onView(withText("Yes")).perform(click())
    }

    @Test
    fun deleteAllFavoriteTV(){
        onView(withId(R.id.favorite)).perform(click())
        swipeLeftFavorite()
        onView(withId(R.id.delete)).perform(click())
        onView(withText("Yes")).perform(click())
    }

    @Test
    fun loadViewPagerFavoriteTest(){
        onView(withId(R.id.favorite)).perform(click())
        checkViewPagerItemsFavorite()
        swipeLeftFavorite()
        swipeRightFavorite()
    }

    private fun checkViewPagerItems(){
        val viewPager = activityRule.activity.findViewById<ViewPager>(R.id.view_pager)
        val title = 2
        assertNotNull(viewPager)
        assertNotNull(viewPager.adapter)
        assertTrue(title == viewPager.adapter?.count)
    }

    private fun checkViewPagerItemsFavorite(){
        val viewPager = activityRules.activity.findViewById<ViewPager>(R.id.view_pagers)
        val title = 2
        assertNotNull(viewPager)
        assertNotNull(viewPager.adapter)
        assertTrue(title == viewPager.adapter?.count)
    }

    private fun swipeLeft(){
        val viewPager = onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        viewPager.perform(ViewActions.swipeLeft())
    }

    private fun swipeLeftFavorite(){
        val viewPager = onView(withId(R.id.view_pagers)).check(matches(isDisplayed()))
        viewPager.perform(ViewActions.swipeLeft())
    }

    private fun swipeRight(){
        val viewPager = onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        viewPager.perform(ViewActions.swipeRight())
    }

    private fun swipeRightFavorite(){
        val viewPager = onView(withId(R.id.view_pagers)).check(matches(isDisplayed()))
        viewPager.perform(ViewActions.swipeRight())
    }

    private fun swipeUp(id : Int){
        val recyclerView = onView(withId(id)).check(matches(isDisplayed()))
        recyclerView.perform(ViewActions.swipeUp())
    }


}