package com.pedroluis.projects.notepad.commons.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import androidx.core.content.edit

private const val NOTEPAD_PREFERENCES = "notepad_preferences"
private const val NOTEPAD_LIST = "notepad_list"

internal class PreferencesData(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(NOTEPAD_PREFERENCES, Context.MODE_PRIVATE)
    private val gson = Gson()
    
    fun saveUser(note: NotepadModel) {
        val notes = getNotes().toMutableList()
        val index = notes.indexOfFirst { it.id == note.id }

        if (index != -1) {
            notes[index] = note
        } else {
            notes.add(note)
        }

        saveNotesToPrefs(notes)
    }

    fun getNotes(): List<NotepadModel> {
        val json = sharedPreferences.getString(NOTEPAD_LIST, null) ?: return emptyList()
        val type = object : TypeToken<List<NotepadModel>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getNoteById(id: String): NotepadModel? {
        return getNotes().find { it.id == id }
    }

    fun deleteNote(id: String) {
        val notes = getNotes().filter { it.id != id }
        saveNotesToPrefs(notes)
    }

    private fun saveNotesToPrefs(notes: List<NotepadModel>) {
        val json = gson.toJson(notes)
        sharedPreferences.edit { putString(NOTEPAD_LIST, json) }
    }
}
