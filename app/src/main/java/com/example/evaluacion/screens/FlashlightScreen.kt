package com.example.evaluacion.screens

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evaluacion.MyApp
import com.example.evaluacion.R

@Composable
fun FlashlightScreen() {
    var flashlightOn by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF020617),
                        Color(0xFF0F172A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Control de Linterna",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Botón circular grande
            IconButton(
                onClick = {
                    flashlightOn = !flashlightOn
                    toggleFlashlight(flashlightOn)
                },
                modifier = Modifier
                    .size(140.dp)
                    .background(
                        color = if (flashlightOn) 
                                    Color(0xFFF59E0B).copy(alpha = 0.2f) // Ambar encendido
                                else 
                                    Color(0xFF1D283A),               // Gris oscuro apagado
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_flashlight),
                    contentDescription = "Flashlight",
                    tint = if (flashlightOn) Color(0xFFF59E0B) else Color(0xFF6B7280),
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (flashlightOn) "Linterna ENCENDIDA" else "Linterna APAGADA",
                color = if (flashlightOn) Color(0xFFF59E0B) else Color(0xFF9CA3AF),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Presiona el ícono para alternar",
                color = Color(0xFF6B7280),
                fontSize = 14.sp
            )
        }
    }
}

@SuppressLint("ServiceCast")
fun toggleFlashlight(turnOn: Boolean) {
    try {
        val cameraManager = MyApp.instance.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
            cameraManager.getCameraCharacteristics(id)
                .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
        }
        if (cameraId != null) {
            cameraManager.setTorchMode(cameraId, turnOn)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
