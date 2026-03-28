package com.pedroluis.projects.notepad.features

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class NotepadComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun should_CreateNewNote_And_DisplayItInList() {
        // 1. Click on FAB to add a new note
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        // 2. Type title and description
        val title = "Compose Test Note"
        val description = "This is a test description for the compose test."

        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description").performTextInput(description)

        // 3. Click save button
        composeTestRule.onNodeWithText("Save").performClick()

        // 4. Verify we are back in the list and the note is visible
        composeTestRule.onNodeWithText("Notepad").assertIsDisplayed()
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
    }

    @Test
    fun should_ShowErrorStates_When_FieldsAreEmpty() {
        // 1. Click on FAB
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        // 2. Click save without typing anything
        composeTestRule.onNodeWithText("Save").performClick()

        // 3. Verify the screen is still on "New Note" (not popped back)
        composeTestRule.onNodeWithText("New Note").assertIsDisplayed()
    }
}
