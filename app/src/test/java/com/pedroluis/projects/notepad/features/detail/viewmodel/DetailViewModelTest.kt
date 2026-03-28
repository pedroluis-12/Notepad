package com.pedroluis.projects.notepad.features.detail.viewmodel

import app.cash.turbine.test
import com.pedroluis.projects.notepad.features.detail.usecase.DetailUseCase
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import com.pedroluis.projects.notepad.features.detail.viewmodel.state.DetailViewState
import com.pedroluis.projects.notepad.commons.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<DetailUseCase>()
    private val viewModel = DetailViewModel(useCase)

    @Test
    fun `saveNote should update dataResult with DisplaySuccess when useCase returns Success`() = runTest {
        coEvery { useCase.saveNote(any(), any(), any()) } returns DetailUseCaseState.Success

        viewModel.dataResult.test {
            assertEquals(DetailViewState.Idle, awaitItem())
            viewModel.saveNote(title = "Title", description = "Desc")
            assertEquals(DetailViewState.DisplaySuccess, awaitItem())
        }
    }

    @Test
    fun `saveNote should update dataResult with DisplayErrorTitle when useCase returns ErrorTitle`() = runTest {
        coEvery { useCase.saveNote(any(), any(), any()) } returns DetailUseCaseState.ErrorTitle

        viewModel.dataResult.test {
            assertEquals(DetailViewState.Idle, awaitItem())
            viewModel.saveNote(title = "", description = "Desc")
            assertEquals(DetailViewState.DisplayErrorTitle, awaitItem())
        }
    }

    @Test
    fun `saveNote should update dataResult with DisplayErrorDescription when useCase returns ErrorDescription`() = runTest {
        coEvery { useCase.saveNote(any(), any(), any()) } returns DetailUseCaseState.ErrorDescription

        viewModel.dataResult.test {
            assertEquals(DetailViewState.Idle, awaitItem())
            viewModel.saveNote(title = "Title", description = "")
            assertEquals(DetailViewState.DisplayErrorDescription, awaitItem())
        }
    }

    @Test
    fun `saveNote should update dataResult with DisplayErrorGeneral when useCase returns ErrorGeneral`() = runTest {
        coEvery { useCase.saveNote(any(), any(), any()) } returns DetailUseCaseState.ErrorGeneral

        viewModel.dataResult.test {
            assertEquals(DetailViewState.Idle, awaitItem())
            viewModel.saveNote(title = "", description = "")
            assertEquals(DetailViewState.DisplayErrorGeneral, awaitItem())
        }
    }
}
