package com.agenda.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.agenda.app.ui.components.NoteCard
import com.agenda.app.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    viewModel: NotesViewModel,
    onCreateNote: () -> Unit,
    onEditNote: (String) -> Unit,
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit,
    isDarkMode: Boolean
) {
    val notes by viewModel.notes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var isSearchVisible by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<String?>(null) }

    val filteredNotes = viewModel.getFilteredNotes()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Minhas Notas",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Icon(
                            imageVector = if (isSearchVisible) Icons.Filled.Close
                            else Icons.Filled.Search,
                            contentDescription = "Buscar"
                        )
                    }
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = "Configurações")
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Filled.Logout, contentDescription = "Sair")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateNote,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Nova Nota",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            AnimatedVisibility(
                visible = isSearchVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryChange,
                    placeholder = { Text("Buscar notas...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                Icon(Icons.Filled.Close, contentDescription = "Limpar")
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            if (filteredNotes.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.NoteAlt,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (searchQuery.isNotEmpty()) "Nenhuma nota encontrada"
                            else "Nenhuma nota ainda",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                        Text(
                            text = if (searchQuery.isNotEmpty()) "Tente outro termo de busca"
                            else "Toque no + para criar sua primeira nota",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            } else {
                // Notes grid (Google Keep style)
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredNotes, key = { it.id }) { note ->
                        NoteCard(
                            note = note,
                            isDarkMode = isDarkMode,
                            onClick = { onEditNote(note.id) },
                            onLongClick = { noteToDelete = note.id }
                        )
                    }
                }
            }
        }
    }

    // Delete confirmation dialog
    if (noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { noteToDelete = null },
            title = { Text("Excluir nota") },
            text = { Text("Tem certeza que deseja excluir esta nota? Esta ação não pode ser desfeita.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        noteToDelete?.let { viewModel.deleteNote(it) }
                        noteToDelete = null
                    }
                ) {
                    Text("Excluir", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { noteToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
