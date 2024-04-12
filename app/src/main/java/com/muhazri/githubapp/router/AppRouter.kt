package com.muhazri.githubapp.router

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhazri.githubapp.presentation.view.favourite.FavouriteScreen
import com.muhazri.githubapp.presentation.view.home.HomeScreen
import com.muhazri.githubapp.presentation.view.profile.ProfileScreen
import com.muhazri.githubapp.presentation.view.settings.SettingsScreen
import com.muhazri.githubapp.presentation.view.splash.SplashScreen

class AppRouter {
     companion object{
         fun navigateAndReset(navController: NavController, routeName: String){
             navController.popBackStack(navController.graph.startDestinationId, true)
             navController.graph.setStartDestination(routeName)
             navController.navigate(routeName)
         }

         fun replaceScreen(navController: NavController,route: String) {
             navController.navigate(route) {
                 launchSingleTop = true
                 popUpTo(route)
             }
         }
     }

    @Composable
    fun AppNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String = Screen.Splash.name,
    ) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier
            ) {
                NavigationItem.objectList().forEach { item ->
                    composable(

                        route = item.route,
                        enterTransition = {
                            scaleIntoContainer()
                        },
                        exitTransition = {
                            scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
                        },
                        popEnterTransition = {
                            scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
                        },
                        popExitTransition = {
                            scaleOutOfContainer()
                        }

                    ) {

                            backStackEntry ->

                        when (item) {
                            is NavigationItem.Splash -> {
                                SplashScreen(navController = navController)
                            }
                            is NavigationItem.Home -> HomeScreen(navController = navController)
                            is NavigationItem.Profile -> ProfileScreen(navController= navController,login = backStackEntry.arguments?.getString("login")!!)
                            is NavigationItem.Favourite -> FavouriteScreen(navController = navController)
                            is NavigationItem.Settings -> SettingsScreen(navController = navController)
                            else -> {}
                        }
                    }
                }
            }
    }

    private fun scaleIntoContainer(
        direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
        initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.9f else 1.1f
    ): EnterTransition {
        return scaleIn(
            animationSpec = tween(220, delayMillis = 90),
            initialScale = initialScale
        ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
    }

    private fun scaleOutOfContainer(
        direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
        targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.9f else 1.1f
    ): ExitTransition {
        return scaleOut(
            animationSpec = tween(
                durationMillis = 220,
                delayMillis = 90
            ), targetScale = targetScale
        ) + fadeOut(tween(delayMillis = 90))
    }
}


enum class Screen {
    Splash,
    Home,
    Profile,
    Favourite,
    Settings,
}

enum class ScaleTransitionDirection {
    INWARDS,
    OUTWARDS,
}


sealed class NavigationItem(val route: String) {
    data object Splash: NavigationItem(Screen.Splash.name)
    data object Home : NavigationItem(Screen.Home.name)
    data object Profile : NavigationItem("${Screen.Profile.name}/{login}")
    data object Favourite: NavigationItem(Screen.Favourite.name)
    data object Settings: NavigationItem(Screen.Settings.name)

    companion object {
        fun objectList(): Array<NavigationItem> {
            return arrayOf(Splash,Home, Profile,Favourite,Settings)
        }
    }
}