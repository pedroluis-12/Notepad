package com.pedroluis.projects.notepad.commons.di

import com.pedroluis.projects.notepad.commons.data.PreferencesData
import com.pedroluis.projects.notepad.features.detail.repository.DetailRepository
import com.pedroluis.projects.notepad.features.detail.repository.DetailRepositoryImpl
import com.pedroluis.projects.notepad.features.detail.usecase.DetailUseCase
import com.pedroluis.projects.notepad.features.detail.usecase.DetailUseCaseImpl
import com.pedroluis.projects.notepad.features.detail.viewmodel.DetailViewModel
import com.pedroluis.projects.notepad.features.list.repository.ListRepository
import com.pedroluis.projects.notepad.features.list.repository.ListRepositoryImpl
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCase
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCaseImpl
import com.pedroluis.projects.notepad.features.list.viewmodel.ListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { PreferencesData(androidContext()) }

    // List feature
    single<ListRepository> { ListRepositoryImpl(get()) }
    single<ListUseCase> { ListUseCaseImpl(get()) }
    viewModel { ListViewModel(get()) }

    // Detail feature
    single<DetailRepository> { DetailRepositoryImpl(get()) }
    single<DetailUseCase> { DetailUseCaseImpl(get()) }
    viewModel { DetailViewModel(get()) }
}
