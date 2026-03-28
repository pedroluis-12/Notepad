package com.pedroluis.projects.notepad.features.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.features.list.viewmodel.ListViewModel
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListGetViewState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListScreen(
    onAddNote: () -> Unit,
    onEditNote: (String, String, String) -> Unit,
    viewModel: ListViewModel = koinViewModel()
) {
    val listState by viewModel.listGetResult.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNotes()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notepad") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = listState) {
                is ListGetViewState.DisplaySuccess -> {
                    LazyColumn {
                        items(state.notes) { note ->
                            NoteItem(
                                note = note,
                                onClick = { onEditNote(note.id, note.title, note.description) },
                                onDelete = { viewModel.deleteNote(0, note.id) }
                            )
                        }
                    }
                }
                is ListGetViewState.DisplayEmptyList -> {
                    Text(
                        text = "No notes registered",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                else -> Unit
            }
        }
    }
}

@Composable
fun NoteItem(
    note: NotepadModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, style = MaterialTheme.typography.titleLarge)
                Text(text = note.description, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
