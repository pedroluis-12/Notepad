package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.model.NotepadModel

class ListRepositoryImpl : ListRepository {
    override fun getNotes(): List<NotepadModel> {
        val list = listOf(
            NotepadModel("teste", "teste"),
            NotepadModel("teste", "teste"),
            NotepadModel("teste", "teste")
        )
        return list
    }

    override fun deleteNote(index: Int) {

    }
}