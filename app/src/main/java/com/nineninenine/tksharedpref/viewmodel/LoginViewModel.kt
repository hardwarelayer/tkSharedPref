package com.nineninenine.tksharedpref.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nineninenine.tksharedpref.utils.LanguagePreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val languagePreferenceManager: LanguagePreferenceManager
) : ViewModel() {
    
    private val _currentLanguage = MutableStateFlow("")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()
    
    init {
        loadCurrentLanguage()
    }
    
    private fun loadCurrentLanguage() {
        viewModelScope.launch {
            val language = languagePreferenceManager.getCurrentLanguage()
            val displayName = languagePreferenceManager.getLanguageDisplayName(language)
            _currentLanguage.value = displayName
        }
    }
    
    fun onLoginClick() {
        // Login logic can be added here if needed
    }
}
