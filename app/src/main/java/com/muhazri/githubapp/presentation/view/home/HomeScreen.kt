package com.muhazri.githubapp.presentation.view.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.muhazri.githubapp.data.datasources.UserRemoteDataSource
import com.muhazri.githubapp.data.viewmodel.HomeViewModel
import com.muhazri.githubapp.presentation.component.UserTile
import com.muhazri.githubapp.router.NavigationItem

private fun onNavigateToFavourite(navController: NavController){
    navController.navigate(NavigationItem.Favourite.route)
}

private fun onNavigateToSettings(navController: NavController){
    navController.navigate(NavigationItem.Settings.route)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.submitSearchQuery("Azri")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Github App")
                },
                actions = {
                    IconButton(onClick = { onNavigateToFavourite(navController = navController) }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favourite Users",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    IconButton(onClick = { onNavigateToSettings(navController = navController) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
            )

        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            TextField(
                placeholder = {
                    Text("Search")
                },
                value = state.query,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.submitSearchQuery(state.query)
                    focusManager.clearFocus()
                })
            )

            if(state.isLoading){
                CircularProgressIndicator()
            }
            else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(state.users.size) { index ->

                        UserTile(
                            imageUrl = state.users[index].avatarUrl,
                            name = state.users[index].login,
                            onClick = {
                                navController.navigate("Profile/${state.users[index].login}") {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    errorMessage?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        viewModel.resetErrorData()
    }



}

@Preview
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()
    val userRemoteDataSource= UserRemoteDataSource()
    HomeScreen(navController = navController, viewModel = HomeViewModel(userRemoteDataSource))
}