package com.agenda.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.agenda.app.navigation.AgendaNavGraph
import com.agenda.app.ui.theme.AgendaTheme
import com.agenda.app.viewmodel.AuthViewModel
import com.agenda.app.viewmodel.NotesViewModel
import com.agenda.app.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val notesViewModel: NotesViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingsState by settingsViewModel.state.collectAsState()

            AgendaTheme(darkTheme = settingsState.isDarkMode) {
                val navController = rememberNavController()

                AgendaNavGraph(
                    navController = navController,
                    authViewModel = authViewModel,
                    notesViewModel = notesViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}
