package com.pedroluis.projects.notepad.features.detail.usecase

import com.pedroluis.projects.notepad.features.detail.repository.DetailRepository
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailUseCaseImplTest {

    private val repository = mockk<DetailRepository>()
    private val useCase = DetailUseCaseImpl(repository)

    @Test
    fun `saveNote should return ErrorGeneral when title and description are empty`() = runTest {
        val result = useCase.saveNote(title = "", description = "")
        assertEquals(DetailUseCaseState.ErrorGeneral, result)
    }

    @Test
    fun `saveNote should return ErrorTitle when title is empty`() = runTest {
        val result = useCase.saveNote(title = "", description = "Some description")
        assertEquals(DetailUseCaseState.ErrorTitle, result)
    }

    @Test
    fun `saveNote should return ErrorDescription when description is empty`() = runTest {
        val result = useCase.saveNote(title = "Some title", description = "")
        assertEquals(DetailUseCaseState.ErrorDescription, result)
    }

    @Test
    fun `saveNote should call repository saveNote and return Success when id is null`() = runTest {
        val title = "Title"
        val description = "Description"
        coEvery { repository.saveNote(title, description) } returns Unit

        val result = useCase.saveNote(index = null, title = title, description = description)

        assertEquals(DetailUseCaseState.Success, result)
        coVerify(exactly = 1) { repository.saveNote(title, description) }
    }

    @Test
    fun `saveNote should call repository editNote and return Success when id is not null`() = runTest {
        val id = "1"
        val title = "Title"
        val description = "Description"
        coEvery { repository.editNote(id, title, description) } returns Unit

        val result = useCase.saveNote(index = id, title = title, description = description)

        assertEquals(DetailUseCaseState.Success, result)
        coVerify(exactly = 1) { repository.editNote(id, title, description) }
    }
}
