@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.evaluacion.screens

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.evaluacion.R

@Composable
fun SensorsScreen(nav: NavHostController) {
    val context = LocalContext.current

    val cameraManager = remember {
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }
    val cameraId = remember {
        cameraManager.cameraIdList.firstOrNull()
    }

    var isFlashOn by rememberSaveable { mutableStateOf(false) }
    var flashError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isFlashOn) {
        if (cameraId != null) {
            try {
                cameraManager.setTorchMode(cameraId, isFlashOn)
                flashError = null
            } catch (e: Exception) {
                flashError = "No se pudo controlar la linterna"
            }
        } else {
            flashError = "Este dispositivo no tiene flash disponible"
        }
    }

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
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Datos de sensores",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Panel de estado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFCBD5F5)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0B1120)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_flashlight),
                            contentDescription = "Linterna",
                            modifier = Modifier.size(32.dp),
                            tint = if (isFlashOn) Color(0xFFF59E0B) else Color(0xFF9CA3AF)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Linterna del dispositivo",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Text(
                                text = if (isFlashOn) "Estado: Encendida" else "Estado: Apagada",
                                fontSize = 13.sp,
                                color = Color(0xFF9CA3AF)
                            )
                        }
                    }

                    Switch(
                        checked = isFlashOn,
                        onCheckedChange = { isFlashOn = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF60A5FA),
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color(0xFF1D283A)
                        )
                    )
                }

                if (flashError != null) {
                    Text(
                        text = flashError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0B1120)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Lecturas de sensores",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "• Temperatura ambiente: 23.4 °C (simulada)", color = Color(0xFF9CA3AF))
                    Text(text = "• Humedad relativa: 45 % (simulada)", color = Color(0xFF9CA3AF))
                    Text(text = "• Nivel de luz: 720 lx (simulada)", color = Color(0xFF9CA3AF))
                }
            }
        }
    }
}
