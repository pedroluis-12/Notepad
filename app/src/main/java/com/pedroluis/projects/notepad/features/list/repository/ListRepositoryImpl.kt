package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.data.PreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel

internal class ListRepositoryImpl(
    private val preferencesData: PreferencesData
) : ListRepository {

    override suspend fun getNotes(): List<NotepadModel> =
        preferencesData.getNotes()

    override suspend fun deleteNote(id: String) {
        preferencesData.deleteNote(id)
    }
}