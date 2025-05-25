package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.model.NotepadModel

class ListRepositoryImpl : ListRepository {
    override fun getNotes(): List<NotepadModel> {
        val list = listOf(
            NotepadModel(1,"teste-1", "teste"),
            NotepadModel(2,"teste-2", "teste"),
            NotepadModel(3,"teste-3", "teste")
        )
        return list
    }

    override fun deleteNote(item: NotepadModel) {

    }
}