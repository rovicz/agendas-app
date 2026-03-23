package com.agenda.app.data

import com.agenda.app.model.Note
import com.agenda.app.model.NoteColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object NotesRepository {

    private val _notes = MutableStateFlow(
        listOf(
            Note(
                title = "Bem-vindo ao Agenda App!",
                content = "Este é seu aplicativo de notas. Toque no botão + para criar uma nova nota.",
                color = NoteColor.BLUE
            ),
            Note(
                title = "Lista de Compras",
                content = "• Arroz\n• Feijão\n• Café\n• Leite\n• Pão",
                color = NoteColor.YELLOW
            ),
            Note(
                title = "Ideias para o projeto",
                content = "Implementar modo escuro, adicionar categorias, sincronizar com a nuvem.",
                color = NoteColor.PURPLE
            )
        )
    )

    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(note: Note) {
        _notes.update { currentList -> listOf(note) + currentList }
    }

    fun updateNote(note: Note) {
        _notes.update { currentList ->
            currentList.map {
                if (it.id == note.id) note.copy(updatedAt = System.currentTimeMillis())
                else it
            }
        }
    }

    fun deleteNote(noteId: String) {
        _notes.update { currentList -> currentList.filter { it.id != noteId } }
    }

    fun getNoteById(noteId: String): Note? {
        return _notes.value.find { it.id == noteId }
    }
}
