package com.pedroluis.projects.notepad.features.detail.repository

import android.content.Context
import com.pedroluis.projects.notepad.commons.data.PreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import java.util.UUID

class DetailRepositoryImpl: DetailRepository {

    override fun saveNote(context: Context, title: String, description: String) {
        val pref = PreferencesData(context)
        val note = NotepadModel(
            id = generateRandomUniqueIdString(), title = title, description = description
        )
        pref.saveUser(note)
    }

    override fun editNote(context: Context, id: String, title: String, description: String) {
        val pref = PreferencesData(context)
        val note = NotepadModel(id = id, title = title, description = description)
        pref.saveUser(note)
    }

    private fun generateRandomUniqueIdString(): String {
        return UUID.randomUUID().toString()
    }
}
