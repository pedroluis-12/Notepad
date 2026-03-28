package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.data.PreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel

internal class ListRepositoryImpl(
    private val preferencesData: PreferencesData
) : ListRepository {

    override fun getNotes(): List<NotepadModel> {
        return preferencesData.getNotes()
    }

    override fun deleteNote(id: String) {
        preferencesData.deleteNote(id)
    }
}