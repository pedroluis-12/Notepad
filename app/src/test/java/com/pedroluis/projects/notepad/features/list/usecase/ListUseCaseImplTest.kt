package com.pedroluis.projects.notepad.features.list.usecase

import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.features.list.repository.ListRepository
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ListUseCaseImplTest {

    private val repository = mockk<ListRepository>()
    private val useCase = ListUseCaseImpl(repository)

    @Test
    fun `getNotes should return Success when repository returns non-empty list`() = runTest {
        val notes = listOf(NotepadModel("1", "Title", "Desc"))
        coEvery { repository.getNotes() } returns notes

        val result = useCase.getNotes()

        assertEquals(ListGetUseCaseState.Success(notes), result)
    }

    @Test
    fun `getNotes should return EmptyList when repository returns empty list`() = runTest {
        coEvery { repository.getNotes() } returns emptyList()

        val result = useCase.getNotes()

        assertEquals(ListGetUseCaseState.EmptyList, result)
    }

    @Test
    fun `deleteNote should return DeleteSuccess when id is not null`() = runTest {
        val index = 0
        val id = "1"
        coEvery { repository.deleteNote(id) } returns Unit

        val result = useCase.deleteNote(index, id)

        assertEquals(ListDeleteUseCaseState.DeleteSuccess(index), result)
        coVerify(exactly = 1) { repository.deleteNote(id) }
    }

    @Test
    fun `deleteNote should return Error when id is null`() = runTest {
        val result = useCase.deleteNote(0, null)

        assertEquals(ListDeleteUseCaseState.Error, result)
        coVerify(exactly = 0) { repository.deleteNote(any()) }
    }
}
