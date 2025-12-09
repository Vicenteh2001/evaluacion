package com.example.evaluacion.screens.led

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedControlScreen(
    viewModel: LedViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Control de LEDs IoT") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
            }

            Text(
                "Control de 3 LEDs conectados a NodeMCU",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))

            // LED 1
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LED 1", modifier = Modifier.weight(1f))
                Switch(
                    checked = uiState.led1,
                    onCheckedChange = { viewModel.onToggleLed(1, it) }
                )
            }

            // LED 2
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("LED 2", modifier = Modifier.weight(1f))
                Switch(
                    checked = uiState.led2,
                    onCheckedChange = { viewModel.onToggleLed(2, it) }
                )
            }

            // LED 3
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("LED 3", modifier = Modifier.weight(1f))
                Switch(
                    checked = uiState.led3,
                    onCheckedChange = { viewModel.onToggleLed(3, it) }
                )
            }

            Spacer(Modifier.height(24.dp))

            uiState.error?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
