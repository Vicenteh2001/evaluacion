// SplashScreen.kt
package com.example.evaluacion.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.evaluacion.R
import com.example.evaluacion.nav.Route
import kotlinx.coroutines.delay
import androidx.compose.material3.ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(nav: NavHostController) {

    // Cargar animación desde res/raw/splash.json
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash)
    )

    // Progreso de la animación
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        isPlaying = true,
        speed = 1.0f
    )

    // Navegar al login después de unos segundos
    LaunchedEffect(Unit) {
        delay(3000) // 3 segundos de splash

        // limpiamos el back stack del splash y vamos a login
        nav.popBackStack()
        nav.navigate(Route.Login.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020617)),
        contentAlignment = Alignment.Center
    ) {
        if (composition != null) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(260.dp)
            )
        }
    }
}
