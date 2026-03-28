package com.pedroluis.projects.notepad.features.detail.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pedroluis.projects.notepad.features.detail.viewmodel.DetailViewModel
import com.pedroluis.projects.notepad.features.detail.viewmodel.state.DetailViewState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(
    id: String?,
    title: String?,
    description: String?,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    var titleState by remember { mutableStateOf(title ?: "") }
    var descriptionState by remember { mutableStateOf(description ?: "") }
    val detailState by viewModel.dataResult.collectAsState()

    LaunchedEffect(detailState) {
        if (detailState is DetailViewState.DisplaySuccess) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (id == null) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titleState,
                onValueChange = { titleState = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                isError = detailState is DetailViewState.DisplayErrorTitle || detailState is DetailViewState.DisplayErrorGeneral
            )

            OutlinedTextField(
                value = descriptionState,
                onValueChange = { descriptionState = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().weight(1f),
                isError = detailState is DetailViewState.DisplayErrorDescription || detailState is DetailViewState.DisplayErrorGeneral
            )

            Button(
                onClick = { viewModel.saveNote(id, titleState, descriptionState) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
