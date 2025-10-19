package com.nineninenine.tksharedpref.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExampleViewModel : ViewModel() {
    
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()
    
    private val _showMessage = MutableStateFlow("")
    val showMessage: StateFlow<String> = _showMessage.asStateFlow()
    
    fun updateInputText(text: String) {
        _inputText.value = text
    }
    
    fun onSubmitClick() {
        viewModelScope.launch {
            if (_inputText.value.isNotEmpty()) {
                _showMessage.value = "Submitted: ${_inputText.value}"
            } else {
                _showMessage.value = "Please enter some text"
            }
        }
    }
    
    fun clearMessage() {
        _showMessage.value = ""
    }
}
