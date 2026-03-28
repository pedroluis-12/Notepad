package com.pedroluis.projects.notepad.features

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pedroluis.projects.notepad.R
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotepadFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun should_CreateNewNote_And_DisplayItInList() {
        // 1. Click on float button to add a new note
        onView(withId(R.id.fab_add_note))
            .perform(click())

        // 2. Type title and description
        val title = "Test Note Title"
        val description = "This is a test description for the espresso test."

        onView(withId(R.id.titleEditText))
            .perform(typeText(title), closeSoftKeyboard())

        onView(withId(R.id.descriptionEditText))
            .perform(typeText(description), closeSoftKeyboard())

        // 3. Click save button
        onView(withId(R.id.saveButton))
            .perform(click())

        // 4. Verify we are back in the list and the note is visible
        onView(withId(R.id.list_note))
            .check(matches(isDisplayed()))

        onView(withText(title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_ShowErrorMessages_When_FieldsAreEmpty() {
        // 1. Click on float button
        onView(withId(R.id.fab_add_note))
            .perform(click())

        // 2. Click save without typing anything
        onView(withId(R.id.saveButton))
            .perform(click())

        // 3. Verify error messages are displayed (using TextInputLayout error state)
        onView(withId(R.id.titleInputLayout))
            .check(matches(hasDescendant(withText(not("")))))
        
        onView(withId(R.id.descriptionInputLayout))
            .check(matches(hasDescendant(withText(not("")))))
    }
}
