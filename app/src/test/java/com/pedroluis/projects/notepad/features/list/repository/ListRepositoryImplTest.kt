package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.data.NotepadPreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ListRepositoryImplTest {

    private val notepadPreferencesData = mockk<NotepadPreferencesData>()
    private val repository = ListRepositoryImpl(notepadPreferencesData)

    @Test
    fun `getNotes should call preferencesData getNotes`() = runTest {
        val expectedNotes = listOf(NotepadModel("1", "Title", "Desc"))
        coEvery { notepadPreferencesData.getNotes() } returns expectedNotes

        val result = repository.getNotes()

        assertEquals(expectedNotes, result)
        coVerify(exactly = 1) { notepadPreferencesData.getNotes() }
    }

    @Test
    fun `deleteNote should call preferencesData deleteNote`() = runTest {
        val noteId = "1"
        coEvery { notepadPreferencesData.deleteNote(noteId) } returns Unit

        repository.deleteNote(noteId)

        coVerify(exactly = 1) { notepadPreferencesData.deleteNote(noteId) }
    }
}
