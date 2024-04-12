package com.muhazri.githubapp.presentation.view.splash


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.muhazri.githubapp.router.AppRouter
import com.muhazri.githubapp.router.NavigationItem
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xffFFFFFF)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(com.muhazri.githubapp.R.drawable.github_logo),
                contentDescription = "GitHub Logo",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }


    LaunchedEffect(navController) {
        delay(3000)
        AppRouter.navigateAndReset(navController, NavigationItem.Home.route)
    }
}


