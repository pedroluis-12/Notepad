package com.pedroluis.projects.notepad.features.detail.repository

import com.pedroluis.projects.notepad.commons.data.NotepadPreferencesData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DetailRepositoryImplTest {

    private val notepadPreferencesData = mockk<NotepadPreferencesData>()
    private val repository = DetailRepositoryImpl(notepadPreferencesData)

    @Test
    fun `saveNote should call preferencesData saveUser with new note`() = runTest {
        val title = "Title"
        val description = "Description"
        coEvery { notepadPreferencesData.saveUser(any()) } returns Unit

        repository.saveNote(title, description)

        coVerify(exactly = 1) { notepadPreferencesData.saveUser(match { it.title == title && it.description == description }) }
    }

    @Test
    fun `editNote should call preferencesData saveUser with existing note`() = runTest {
        val id = "1"
        val title = "Title"
        val description = "Description"
        coEvery { notepadPreferencesData.saveUser(any()) } returns Unit

        repository.editNote(id, title, description)

        coVerify(exactly = 1) { notepadPreferencesData.saveUser(match { it.id == id && it.title == title && it.description == description }) }
    }
}
