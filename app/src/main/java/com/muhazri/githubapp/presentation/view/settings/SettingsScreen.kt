package com.muhazri.githubapp.presentation.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.muhazri.githubapp.data.prefferences.SharedPreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var isDarkMode by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        isDarkMode = SharedPreferencesManager.getIsDarkMode(context) ?: false
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Settings",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                        )
                    }
                },

                )
        },
    ) {

            innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {
            Row(modifier = Modifier.padding(top = 16.dp)) {
                Text("Dark Mode")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = isDarkMode, // Set initial state from SharedPreferences
                    onCheckedChange = { isChecked ->
                        isDarkMode = isChecked
                        SharedPreferencesManager.setIsDarkMode(context, isChecked)
                        SharedPreferencesManager.applyDarkMode(context)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingsScreen(navController)
}

