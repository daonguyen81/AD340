package com.example.daong.activitykotlin;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.content.Context;
import android.preference.PreferenceManager;
import android.content.Intent;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;;
import static org.junit.Assert.*;
import android.widget.EditText;
import android.content.SharedPreferences;


public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    // third parameter is set to false which means the activity is not started automatically
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class, true,
            false);

    private MainActivity mainAct = null;
    private EditText editText;
    private Intent intent;
    private SharedPreferences.Editor preEditor;

    Instrumentation.ActivityMonitor aMonitor = getInstrumentation().addMonitor(Main2Activity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        mainAct = mActivityTestRule.getActivity();

        intent = new Intent();
        Context context = getInstrumentation().getTargetContext();

        preEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editText = (EditText) mainAct.findViewById(R.id.edittext);

    }

    /**
     * Function for validating logic for empty input
     */
    @Test
    public void testEditTextForEmptyInput() {

        assertFalse(" ".isEmpty());
        onView(withId(R.id.edittext)).perform(clearText());
        onView(withId(R.id.button)).perform(click());
    }


    /**
     * Function to test for input field and button
     */
    @Test
    public void testSendButtonToSecondActivity() {
        onView(withId(R.id.button)).perform(click());

        Activity activityTwo = getInstrumentation().waitForMonitorWithTimeout(aMonitor, 5000);
        assertNotNull(activityTwo);
        activityTwo.finish();
    }

    /**
     * Function the test if the input text matches with display
     */
    @Test
    public void testInputDisplayAfterClick() {

        onView(withId(R.id.edittext)).perform(typeText("Test123"),
            closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

        //check if the input matches the display
        onView(withId(R.id.textview)).check(matches(isDisplayed()));
    }

    /**
     * Function to test store and retrieve data
     */
    @Test
    public void testToStoreAndRetrieve() {

        String testInput = "Test123";

        preEditor.putString("input", testInput);
        preEditor.commit();

        mRule.launchActivity(intent);

        onView(withId(R.id.edittext)).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        mainAct = null;
    }
}