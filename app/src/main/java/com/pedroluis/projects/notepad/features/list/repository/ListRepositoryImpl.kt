package com.pedroluis.projects.notepad.features.list.repository

import android.content.Context
import com.pedroluis.projects.notepad.commons.data.PreferencesData
import com.pedroluis.projects.notepad.commons.model.NotepadModel

class ListRepositoryImpl : ListRepository {

    override fun getNotes(context: Context): List<NotepadModel> {
        val pref = PreferencesData(context)
        val list = pref.getNotes()
        return list
    }

    override fun deleteNote(context: Context, id: String) {
        val pref = PreferencesData(context)
        pref.deleteNote(id)
    }
}