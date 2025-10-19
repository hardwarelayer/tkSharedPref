package com.nineninenine.tksharedpref

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nineninenine.tksharedpref.ui.screens.ExampleScreen
import com.nineninenine.tksharedpref.ui.screens.LoginScreen
import com.nineninenine.tksharedpref.ui.screens.SettingsScreen
import com.nineninenine.tksharedpref.ui.theme.TkSharedPrefTheme
import com.nineninenine.tksharedpref.utils.LanguagePreferenceManager
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Set language before setting content
        setLanguageFromPreferences()
        
        setContent {
            TkSharedPrefTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
    
    private fun setLanguageFromPreferences() {
        val languagePreferenceManager = LanguagePreferenceManager(this)
        val locale = languagePreferenceManager.getCurrentLocale()
        
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToExample = {
                    navController.navigate("example")
                }
            )
        }
        
        composable("example") {
            ExampleScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}