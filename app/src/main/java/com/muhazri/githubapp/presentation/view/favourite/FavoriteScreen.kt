package com.muhazri.githubapp.presentation.view.favourite

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.muhazri.githubapp.data.viewmodel.FavouriteViewModel
import com.muhazri.githubapp.presentation.component.UserTile
import com.muhazri.githubapp.router.AppRouter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(navController: NavController, modifier: Modifier = Modifier,viewModel: FavouriteViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Favourites User",
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
        Column(
            modifier = modifier.padding(innerPadding).verticalScroll(rememberScrollState())
        ) {
            state.favouriteUsers.forEach { user ->
                UserTile(
                    imageUrl = user.avatarUrl,
                    name = user.login,
                    onClick = {
                        AppRouter.replaceScreen(navController,"Profile/${user.login}")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getFavouriteUsers()

    }

    errorMessage?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        viewModel.resetErrorData()
    }
}

@Preview
@Composable
private fun FavouriteScreenPreview() {
    val navController = rememberNavController()
    FavouriteScreen(navController)
}

