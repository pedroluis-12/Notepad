package com.pedroluis.projects.notepad.commons.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val NOTEPAD_PREFERENCES = "notepad_preferences"
private val NOTEPAD_LIST_KEY = stringPreferencesKey("notepad_list")

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NOTEPAD_PREFERENCES)

internal class NotepadPreferencesData(private val context: Context) {

    private val gson = Gson()

    suspend fun saveUser(note: NotepadModel) {
        val notes = getNotes().toMutableList()
        val index = notes.indexOfFirst { it.id == note.id }

        if (index != -1) {
            notes[index] = note
        } else {
            notes.add(note)
        }

        saveNotesToPrefs(notes)
    }

    suspend fun getNotes(): List<NotepadModel> {
        val json = context.dataStore.data
            .map { preferences ->
                preferences[NOTEPAD_LIST_KEY]
            }.first() ?: return emptyList()

        val type = object : TypeToken<List<NotepadModel>>() {}.type
        return gson.fromJson(json, type)
    }

    suspend fun deleteNote(id: String) {
        val notes = getNotes().filter { it.id != id }
        saveNotesToPrefs(notes)
    }

    private suspend fun saveNotesToPrefs(notes: List<NotepadModel>) {
        val json = gson.toJson(notes)
        context.dataStore.edit { preferences ->
            preferences[NOTEPAD_LIST_KEY] = json
        }
    }
}
