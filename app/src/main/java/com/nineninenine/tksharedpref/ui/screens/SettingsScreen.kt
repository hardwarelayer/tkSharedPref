package com.nineninenine.tksharedpref.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nineninenine.tksharedpref.R
import com.nineninenine.tksharedpref.utils.LanguagePreferenceManager
import com.nineninenine.tksharedpref.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val languagePreferenceManager = remember { LanguagePreferenceManager(context) }
    val viewModel = remember { SettingsViewModel(languagePreferenceManager, context) }
    
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val showRestartMessage by viewModel.showRestartMessage.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.settings_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = stringResource(R.string.select_language),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    // English option
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedLanguage == "en",
                                onClick = { viewModel.selectLanguage("en") }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLanguage == "en",
                            onClick = { viewModel.selectLanguage("en") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.english),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    // Vietnamese option
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedLanguage == "vi",
                                onClick = { viewModel.selectLanguage("vi") }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLanguage == "vi",
                            onClick = { viewModel.selectLanguage("vi") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.vietnamese),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = viewModel::saveSettings,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.save_settings))
                    }
                }
            }
        }
    }
    
    // Show restart message dialog
    if (showRestartMessage) {
        AlertDialog(
            onDismissRequest = viewModel::dismissRestartMessage,
            title = { Text(stringResource(R.string.settings_saved)) },
            text = { Text(stringResource(R.string.restart_required)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.dismissRestartMessage()
                        if (context is androidx.activity.ComponentActivity) {
                            viewModel.restartApp(context)
                        }
                    }
                ) {
                    Text("Restart App")
                }
            }
        )
    }
}
