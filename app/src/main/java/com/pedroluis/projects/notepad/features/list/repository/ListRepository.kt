package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.model.NotepadModel

internal interface ListRepository {
    fun getNotes(): List<NotepadModel>
    fun deleteNote(item: NotepadModel)
}
