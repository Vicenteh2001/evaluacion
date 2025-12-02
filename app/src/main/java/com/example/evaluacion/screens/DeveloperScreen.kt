@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.evaluacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DeveloperScreen(nav: NavHostController) {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Datos del desarrollador",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Aplicación Móvil – Evaluación Sumativa 2",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color(0xFFCBD5F5)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0B1120)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Desarrollador",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Text("Nombre: Vicente Hidalgo y Cristobal Gallegos", color = Color(0xFF9CA3AF))
                    Text("Carrera: Analista programador", color = Color(0xFF9CA3AF))
                    Text("Asignatura: Aplicaciones Móviles", color = Color(0xFF9CA3AF))
                    Text("Institución: INACAP", color = Color(0xFF9CA3AF))
                    Text("Correo: vicente@gmail.com", color = Color(0xFF9CA3AF))
                    Text("Rol en el proyecto: Desarrollador Android", color = Color(0xFF9CA3AF))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0B1120)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Resumen técnico de la app",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Text("• Autenticación con backend en AWS (login + registro).", color = Color(0xFF9CA3AF))
                    Text("• Recuperación de contraseña con código.", color = Color(0xFF9CA3AF))
                    Text("• CRUD de usuarios: crear, editar, listar, eliminar.", color = Color(0xFF9CA3AF))
                    Text("• Sensores: linterna real + lecturas simuladas.", color = Color(0xFF9CA3AF))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0B1120)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Herramientas y Tecnologías",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Text("• Kotlin + Jetpack Compose", color = Color(0xFF9CA3AF))
                    Text("• Navigation Compose", color = Color(0xFF9CA3AF))
                    Text("• Material 3 + Lottie", color = Color(0xFF9CA3AF))
                    Text("• Retrofit (API REST)", color = Color(0xFF9CA3AF))
                    Text("• Backend Node.js + Express + SQLite", color = Color(0xFF9CA3AF))
                    Text("• Despliegue AWS EC2", color = Color(0xFF9CA3AF))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = Color(0xFF1D283A))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pantalla creada para cumplir el punto de la rúbrica.",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { nav.popBackStack() }
            ) {
                Text("Volver", color = Color(0xFF60A5FA))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
