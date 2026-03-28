package com.pedroluis.projects.notepad.features.detail.repository

import com.pedroluis.projects.notepad.commons.data.NotepadPreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import java.util.UUID

internal class DetailRepositoryImpl(
    private val preferencesData: NotepadPreferencesData
) : DetailRepository {

    override suspend fun saveNote(title: String, description: String) {
        val note = NotepadModel(
            id = generateRandomUniqueIdString(),
            title = title,
            description = description
        )
        preferencesData.saveUser(note)
    }

    override suspend fun editNote(id: String, title: String, description: String) {
        val note = NotepadModel(id = id, title = title, description = description)
        preferencesData.saveUser(note)
    }

    private fun generateRandomUniqueIdString(): String {
        return UUID.randomUUID().toString()
    }
}
