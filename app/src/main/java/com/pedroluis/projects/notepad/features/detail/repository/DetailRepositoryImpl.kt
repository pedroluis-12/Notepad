package com.pedroluis.projects.notepad.features.detail.repository

import com.pedroluis.projects.notepad.commons.data.PreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import java.util.UUID

internal class DetailRepositoryImpl(
    private val preferencesData: PreferencesData
) : DetailRepository {

    override fun saveNote(title: String, description: String) {
        val note = NotepadModel(
            id = generateRandomUniqueIdString(),
            title = title,
            description = description
        )
        preferencesData.saveUser(note)
    }

    override fun editNote(id: String, title: String, description: String) {
        val note = NotepadModel(id = id, title = title, description = description)
        preferencesData.saveUser(note)
    }

    private fun generateRandomUniqueIdString(): String {
        return UUID.randomUUID().toString()
    }
}
