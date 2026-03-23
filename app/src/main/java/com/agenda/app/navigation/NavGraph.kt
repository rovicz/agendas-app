package com.agenda.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.agenda.app.ui.screens.LoginScreen
import com.agenda.app.ui.screens.NoteEditorScreen
import com.agenda.app.ui.screens.NotesListScreen
import com.agenda.app.ui.screens.SettingsScreen
import com.agenda.app.viewmodel.AuthViewModel
import com.agenda.app.viewmodel.NotesViewModel
import com.agenda.app.viewmodel.SettingsViewModel

@Composable
fun AgendaNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    notesViewModel: NotesViewModel,
    settingsViewModel: SettingsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.NotesList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.NotesList.route) {
            NotesListScreen(
                viewModel = notesViewModel,
                onCreateNote = {
                    notesViewModel.prepareNewNote()
                    navController.navigate(Screen.NoteEditor.createRoute())
                },
                onEditNote = { noteId ->
                    notesViewModel.prepareEditNote(noteId)
                    navController.navigate(Screen.NoteEditor.createRoute(noteId))
                },
                onOpenSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                isDarkMode = settingsViewModel.state.value.isDarkMode
            )
        }

        composable(
            route = "note_editor?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            NoteEditorScreen(
                viewModel = notesViewModel,
                onNavigateBack = { navController.popBackStack() },
                isDarkMode = settingsViewModel.state.value.isDarkMode
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
