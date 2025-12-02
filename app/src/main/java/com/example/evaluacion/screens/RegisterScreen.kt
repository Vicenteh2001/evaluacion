package com.example.evaluacion.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion.auth.AuthUiState
import com.example.evaluacion.auth.AuthViewModel
import com.example.evaluacion.nav.Route
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    nav: NavController,
    viewModel: AuthViewModel
) {
    // ======== STATES LOCALES ========
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }   // se envía como last_name
    var phone by remember { mutableStateOf("") }      // solo local
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    val uiState by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Form válido si tiene nombre, email y clave mínima
    val isFormValid by derivedStateOf {
        name.isNotBlank() &&
                email.isNotBlank() &&
                pwd.length >= 6
    }

    // ============================
    //   REACCIÓN A LOS ESTADOS
    // ============================
    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                val msg = (uiState as AuthUiState.Success).message
                Toast.makeText(
                    context,
                    msg.ifBlank { "Registro exitoso" },
                    Toast.LENGTH_LONG
                ).show()

                // limpiamos los campos
                name = ""
                lastname = ""
                phone = ""
                email = ""
                pwd = ""

                // reseteamos estado
                viewModel.resetState()

                // navegamos al login (sin usar popUpTo para evitar líos)
                nav.popBackStack()
                nav.navigate(Route.Login.route)
            }

            is AuthUiState.Error -> {
                val msg = (uiState as AuthUiState.Error).message
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }

            // estas ramas no hacen nada especial
            AuthUiState.Idle -> Unit
            AuthUiState.Loading -> Unit
        }
    }

    val isLoading = uiState is AuthUiState.Loading

    // ============================
    //   DISEÑO DE LA PANTALLA
    // ============================
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
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Regístrate para continuar",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color(0xFFCBD5F5)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // NOMBRE
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0B1120),
                    unfocusedContainerColor = Color(0xFF0B1120),
                    focusedBorderColor = Color(0xFF60A5FA),
                    unfocusedBorderColor = Color(0xFF1D283A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color(0xFF9CA3AF),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // APELLIDO
            OutlinedTextField(
                value = lastname,
                onValueChange = { lastname = it },
                label = { Text("Apellido") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0B1120),
                    unfocusedContainerColor = Color(0xFF0B1120),
                    focusedBorderColor = Color(0xFF60A5FA),
                    unfocusedBorderColor = Color(0xFF1D283A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color(0xFF9CA3AF),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // TELÉFONO (solo visual)
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono (opcional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0B1120),
                    unfocusedContainerColor = Color(0xFF0B1120),
                    focusedBorderColor = Color(0xFF60A5FA),
                    unfocusedBorderColor = Color(0xFF1D283A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color(0xFF9CA3AF),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0B1120),
                    unfocusedContainerColor = Color(0xFF0B1120),
                    focusedBorderColor = Color(0xFF60A5FA),
                    unfocusedBorderColor = Color(0xFF1D283A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color(0xFF9CA3AF),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // PASSWORD
            OutlinedTextField(
                value = pwd,
                onValueChange = { pwd = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0B1120),
                    unfocusedContainerColor = Color(0xFF0B1120),
                    focusedBorderColor = Color(0xFF60A5FA),
                    unfocusedBorderColor = Color(0xFF1D283A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF60A5FA),
                    unfocusedLabelColor = Color(0xFF9CA3AF),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN REGISTRARSE
            Button(
                onClick = {
                    viewModel.register(
                        name = name.trim(),
                        lastName = lastname.trim(),
                        email = email.trim(),
                        password = pwd
                    )
                },
                enabled = isFormValid && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF60A5FA),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF1D283A),
                    disabledContentColor = Color(0xFF6B7280)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // IR A LOGIN
            TextButton(
                onClick = {
                    nav.popBackStack()
                    nav.navigate(Route.Login.route)
                }
            ) {
                Text(
                    "¿Ya tienes cuenta? Inicia sesión",
                    color = Color(0xFF9CA3AF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
