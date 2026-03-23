package com.agenda.app.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object NotesList : Screen("notes_list")
    data object NoteEditor : Screen("note_editor?noteId={noteId}") {
        fun createRoute(noteId: String? = null): String {
            return if (noteId != null) "note_editor?noteId=$noteId"
            else "note_editor"
        }
    }
    data object Settings : Screen("settings")
}
