package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.data.NotepadPreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel

internal class ListRepositoryImpl(
    private val notepadPreferencesData: NotepadPreferencesData
) : ListRepository {

    override suspend fun getNotes(): List<NotepadModel> =
        notepadPreferencesData.getNotes()

    override suspend fun deleteNote(id: String) {
        notepadPreferencesData.deleteNote(id)
    }
}