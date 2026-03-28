package com.pedroluis.projects.notepad.features.list.viewmodel

import app.cash.turbine.test
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCase
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListDeleteViewState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListGetViewState
import com.pedroluis.projects.notepad.commons.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<ListUseCase>()
    private val viewModel = ListViewModel(useCase)

    @Test
    fun `getNotes should update listGetResult with DisplaySuccess when useCase returns Success`() = runTest {
        val notes = listOf(NotepadModel("1", "Title", "Desc"))
        coEvery { useCase.getNotes() } returns ListGetUseCaseState.Success(notes)

        viewModel.listGetResult.test {
            assertEquals(ListGetViewState.Idle, awaitItem())
            viewModel.getNotes()
            assertEquals(ListGetViewState.DisplaySuccess(notes), awaitItem())
        }
    }

    @Test
    fun `getNotes should update listGetResult with DisplayEmptyList when useCase returns EmptyList`() = runTest {
        coEvery { useCase.getNotes() } returns ListGetUseCaseState.EmptyList

        viewModel.listGetResult.test {
            assertEquals(ListGetViewState.Idle, awaitItem())
            viewModel.getNotes()
            assertEquals(ListGetViewState.DisplayEmptyList, awaitItem())
        }
    }

    @Test
    fun `deleteNote should update listDeleteResult with DisplayDeleteSuccess when useCase returns DeleteSuccess`() = runTest {
        val index = 0
        coEvery { useCase.deleteNote(index, "1") } returns ListDeleteUseCaseState.DeleteSuccess(index)

        viewModel.listDeleteResult.test {
            assertEquals(ListDeleteViewState.Idle, awaitItem())
            viewModel.deleteNote(index, "1")
            assertEquals(ListDeleteViewState.DisplayDeleteSuccess(index), awaitItem())
        }
    }

    @Test
    fun `deleteNote should update listDeleteResult with DisplayError when useCase returns Error`() = runTest {
        coEvery { useCase.deleteNote(any(), any()) } returns ListDeleteUseCaseState.Error

        viewModel.listDeleteResult.test {
            assertEquals(ListDeleteViewState.Idle, awaitItem())
            viewModel.deleteNote(0, "1")
            assertEquals(ListDeleteViewState.DisplayError, awaitItem())
        }
    }
}
