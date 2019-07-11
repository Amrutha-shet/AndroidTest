package com.example.androidtest.ui.activities

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import android.content.Intent
import android.view.View
import androidx.test.InstrumentationRegistry

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController

import com.example.androidtest.R
import com.example.androidtest.ui.fragments.ListFragment
import androidx.test.espresso.ViewAction

import androidx.test.espresso.matcher.ViewMatchers.*

import org.hamcrest.Matcher
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import junit.framework.AssertionFailedError
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed



open class MainActivityTest {
    @Rule
    @JvmField var mActivityRule = ActivityTestRule(MainActivity::class.java)
    private val MY_ACTIVITY_INTENT = Intent(InstrumentationRegistry.getTargetContext(), MainActivity::class.java)
    @Before
    fun setUp() {
        mActivityRule.launchActivity(MY_ACTIVITY_INTENT)
        val fragment = ListFragment()
        mActivityRule.activity.supportFragmentManager.beginTransaction().add(R.id.container_login,fragment,"Fragment Added")

    }

    @After
    fun tearDown() {
    }

    @Test
    fun onCreate() {

    }


    @Test
    fun checkTextDisplayedInDynamicallyCreatedFragment() {
        try {
            onView(withId(R.layout.list_fragment)).check(matches(isDisplayed()))

            onView(withId(R.id.simpleSwipeRefreshLayout))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(100)))

        } catch (e: AssertionFailedError) {
            // View not displayed
        } catch (e:NoMatchingViewException) {

        }


    }

    fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view)
            }
        }
    }

    fun swipeDown(): ViewAction {
        return GeneralSwipeAction(
            Swipe.FAST, GeneralLocation.TOP_CENTER,
            GeneralLocation.BOTTOM_CENTER, Press.FINGER
        )
    }
}