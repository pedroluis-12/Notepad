package com.pedroluis.projects.notepad.features.list.repository

import android.content.Context
import com.pedroluis.projects.notepad.commons.model.NotepadModel

internal interface ListRepository {
    fun getNotes(context: Context): List<NotepadModel>
    fun deleteNote(context: Context, id: String)
}
