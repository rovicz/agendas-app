package com.agenda.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agenda.app.ui.components.ColorPicker
import com.agenda.app.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    viewModel: NotesViewModel,
    onNavigateBack: () -> Unit,
    isDarkMode: Boolean
) {
    val editorState by viewModel.editorState.collectAsState()

    val bgColor = if (isDarkMode) Color(editorState.color.hexDark)
    else Color(editorState.color.hexLight)

    val transparentFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (editorState.isEditing) "Editar Nota" else "Nova Nota",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.saveNote()
                        onNavigateBack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    if (editorState.isEditing) {
                        IconButton(onClick = {
                            editorState.id?.let { viewModel.deleteNote(it) }
                            onNavigateBack()
                        }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Excluir",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bgColor
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(bgColor)
                .imePadding()
        ) {
            // Color picker
            Spacer(modifier = Modifier.height(8.dp))
            ColorPicker(
                selectedColor = editorState.color,
                isDarkMode = isDarkMode,
                onColorSelected = viewModel::onColorChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Title field
            TextField(
                value = editorState.title,
                onValueChange = viewModel::onTitleChange,
                placeholder = {
                    Text(
                        "Título",
                        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                },
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = transparentFieldColors,
                singleLine = true
            )

            // Content field
            TextField(
                value = editorState.content,
                onValueChange = viewModel::onContentChange,
                placeholder = {
                    Text(
                        "Comece a escrever...",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 24.sp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                colors = transparentFieldColors
            )
        }
    }
}
