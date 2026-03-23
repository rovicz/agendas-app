package com.agenda.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthState(
    val isLoggedIn: Boolean = false,
    val username: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun onUsernameChange(value: String) {
        _state.value = _state.value.copy(username = value, errorMessage = null)
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value, errorMessage = null)
    }

    fun login(): Boolean {
        val current = _state.value
        return if (current.username == "root" && current.password == "root") {
            _state.value = current.copy(isLoggedIn = true, errorMessage = null)
            true
        } else {
            _state.value = current.copy(errorMessage = "Usuário ou senha inválidos")
            false
        }
    }

    fun logout() {
        _state.value = AuthState()
    }
}
