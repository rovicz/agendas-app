package com.agenda.app.viewmodel

import androidx.lifecycle.ViewModel
import com.agenda.app.data.NotesRepository
import com.agenda.app.model.Note
import com.agenda.app.model.NoteColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NoteEditorState(
    val id: String? = null,
    val title: String = "",
    val content: String = "",
    val color: NoteColor = NoteColor.DEFAULT,
    val isEditing: Boolean = false
)

class NotesViewModel : ViewModel() {

    val notes: StateFlow<List<Note>> = NotesRepository.notes

    private val _editorState = MutableStateFlow(NoteEditorState())
    val editorState: StateFlow<NoteEditorState> = _editorState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredNotes(): List<Note> {
        val query = _searchQuery.value.lowercase()
        return if (query.isBlank()) {
            notes.value
        } else {
            notes.value.filter {
                it.title.lowercase().contains(query) ||
                    it.content.lowercase().contains(query)
            }
        }
    }

    fun prepareNewNote() {
        _editorState.value = NoteEditorState()
    }

    fun prepareEditNote(noteId: String) {
        val note = NotesRepository.getNoteById(noteId)
        if (note != null) {
            _editorState.value = NoteEditorState(
                id = note.id,
                title = note.title,
                content = note.content,
                color = note.color,
                isEditing = true
            )
        }
    }

    fun onTitleChange(value: String) {
        _editorState.value = _editorState.value.copy(title = value)
    }

    fun onContentChange(value: String) {
        _editorState.value = _editorState.value.copy(content = value)
    }

    fun onColorChange(color: NoteColor) {
        _editorState.value = _editorState.value.copy(color = color)
    }

    fun saveNote(): Boolean {
        val state = _editorState.value
        if (state.title.isBlank() && state.content.isBlank()) return false

        if (state.isEditing && state.id != null) {
            NotesRepository.updateNote(
                Note(
                    id = state.id,
                    title = state.title,
                    content = state.content,
                    color = state.color
                )
            )
        } else {
            NotesRepository.addNote(
                Note(
                    title = state.title,
                    content = state.content,
                    color = state.color
                )
            )
        }
        return true
    }

    fun deleteNote(noteId: String) {
        NotesRepository.deleteNote(noteId)
    }
}
