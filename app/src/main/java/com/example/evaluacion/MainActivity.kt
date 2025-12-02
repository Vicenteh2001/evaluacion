package com.example.evaluacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion.auth.AuthViewModel
import com.example.evaluacion.nav.AppNavGraph
import com.example.evaluacion.ui.theme.EvaluacionTheme
import com.example.evaluacion.auth.AuthViewModelFactory
import com.example.evaluacion.auth.UsersViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EvaluacionTheme {
                Surface {
                    val navController = rememberNavController()

                    val authViewModel: AuthViewModel = viewModel(
                        factory = AuthViewModelFactory()
                    )

                    val usersViewModel: UsersViewModel = viewModel()

                    AppNavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        usersViewModel = usersViewModel
                    )
                }
            }
        }
    }
}
