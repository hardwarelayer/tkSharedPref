package com.nineninenine.tksharedpref.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nineninenine.tksharedpref.utils.AppRestartHelper
import com.nineninenine.tksharedpref.utils.LanguagePreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val languagePreferenceManager: LanguagePreferenceManager,
    private val context: Context
) : ViewModel() {
    
    private val _selectedLanguage = MutableStateFlow("")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()
    
    private val _showRestartMessage = MutableStateFlow(false)
    val showRestartMessage: StateFlow<Boolean> = _showRestartMessage.asStateFlow()
    
    init {
        loadCurrentLanguage()
    }
    
    private fun loadCurrentLanguage() {
        viewModelScope.launch {
            _selectedLanguage.value = languagePreferenceManager.getCurrentLanguage()
        }
    }
    
    fun selectLanguage(language: String) {
        _selectedLanguage.value = language
    }
    
    fun saveSettings() {
        viewModelScope.launch {
            languagePreferenceManager.setLanguage(_selectedLanguage.value)
            _showRestartMessage.value = true
        }
    }
    
    fun dismissRestartMessage() {
        _showRestartMessage.value = false
    }
    
    fun restartApp(activity: Activity) {
        AppRestartHelper.restartApp(activity)
    }
}
