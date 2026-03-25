package com.example.tripitinerary;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.tripitinerary.UI.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testEmptyFields() {
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Invalid credentials")).check(matches(isDisplayed()));
    }

    @Test
    public void testWithFieldsNotCapitalized() {
        onView(withId(R.id.usernameField)).perform(typeText("trip user"));
        onView(withId(R.id.passwordField)).perform(typeText("password"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Invalid credentials")).check(matches(isDisplayed()));
    }

    @Test
    public void testWithFields() {
        onView(withId(R.id.usernameField)).perform(typeText("Trip User"));
        onView(withId(R.id.passwordField)).perform(typeText("password"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("View Vacation Report")).check(matches(isDisplayed()));
    }
}
