package com.nineninenine.tksharedpref.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

class LanguagePreferenceManager(context: Context) {
    
    companion object {
        private const val PREFS_NAME = "language_prefs"
        private const val KEY_LANGUAGE = "selected_language"
        private const val DEFAULT_LANGUAGE = "en"
    }
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    fun getCurrentLanguage(): String {
        return sharedPreferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }
    
    fun setLanguage(language: String) {
        sharedPreferences.edit()
            .putString(KEY_LANGUAGE, language)
            .apply()
    }
    
    fun getCurrentLocale(): Locale {
        val language = getCurrentLanguage()
        return when (language) {
            "vi" -> Locale.forLanguageTag("vi")
            else -> Locale.ENGLISH
        }
    }
    
    fun getLanguageDisplayName(language: String): String {
        return when (language) {
            "vi" -> "Tiếng Việt"
            else -> "English"
        }
    }
}
