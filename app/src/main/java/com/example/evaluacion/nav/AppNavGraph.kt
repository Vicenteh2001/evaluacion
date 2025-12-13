package com.example.evaluacion.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.evaluacion.auth.AuthViewModel
import com.example.evaluacion.screens.*
import com.example.evaluacion.auth.UsersViewModel
import com.example.evaluacion.screens.ForgotPasswordEmailScreen
import com.example.evaluacion.screens.ForgotPasswordCodeScreen
import com.example.evaluacion.screens.ForgotPasswordResetScreen
import com.example.evaluacion.screens.led.LedControlScreen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluacion.screens.led.LedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    usersViewModel: UsersViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(Route.Splash.route) {
            SplashScreen(navController)
        }

        composable(Route.Login.route) {
            LoginScreen(nav = navController, viewModel = authViewModel)
        }

        composable(Route.Register.route) {
            RegisterScreen(nav = navController, viewModel = authViewModel)
        }

        composable(Route.Home.route) {
            HomeScreen(
                nav = navController, 
                viewModel = authViewModel,
                onLogoutDone = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.Home.route) { inclusive = true }
                    }
                },
                onLedClick = {
                    navController.navigate(Route.LedControl.route)
                }
            )
        }

        composable(Route.UsersMenu.route) {
            UsersMenuScreen(nav = navController)
        }

        composable(Route.UserCreate.route) {
            UserCreateScreen(
                nav = navController,
                viewModel = authViewModel,
                usersViewModel = usersViewModel
            )
        }

        composable(Route.UsersList.route) {
            UsersListScreen(
                nav = navController,
                usersViewModel = usersViewModel
            )
        }

        composable(Route.Sensors.route) {
            SensorsScreen(nav = navController)
        }

        composable(Route.LedControl.route) {
            LedControlScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Route.Developer.route) {
            DeveloperScreen(nav = navController)
        }

        composable(Route.ForgotEmail.route) {
            ForgotPasswordEmailScreen(
                nav = navController,
                viewModel = authViewModel
            )
        }

        composable(Route.ForgotCode.route) {
            ForgotPasswordCodeScreen(
                nav = navController,
                viewModel = authViewModel
            )
        }
        composable(Route.LedControl.route) {
            val vm: LedViewModel = viewModel()
            LedControlScreen(viewModel = vm)
        }

        composable(Route.ForgotReset.route) {
            ForgotPasswordResetScreen(
                nav = navController,
                viewModel = authViewModel
            )
        }
    }
}
