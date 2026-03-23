package com.agenda.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsState(
    val isDarkMode: Boolean = false,
    val userName: String = "root"
)

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun toggleDarkMode() {
        _state.value = _state.value.copy(isDarkMode = !_state.value.isDarkMode)
    }
}
