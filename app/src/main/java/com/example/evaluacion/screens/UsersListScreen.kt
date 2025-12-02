package com.example.evaluacion.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.evaluacion.auth.ManagedUser
import com.example.evaluacion.auth.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    nav: NavHostController,
    usersViewModel: UsersViewModel
) {
    val context = LocalContext.current

    val users = usersViewModel.users

    var query by rememberSaveable { mutableStateOf("") }
    var userToEdit by remember { mutableStateOf<ManagedUser?>(null) }
    var userToDelete by remember { mutableStateOf<ManagedUser?>(null) }

    val filtered = remember(users, query) {
        if (query.isBlank()) users
        else users.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.lastName.contains(query, ignoreCase = true) ||
                    it.email.contains(query, ignoreCase = true)
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
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Listado de usuarios",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar por nombre o correo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFF9CA3AF)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
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

            Spacer(modifier = Modifier.height(16.dp))

            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay usuarios registrados.\n" +
                                "Agrega usuarios desde 'Ingresar usuario'.",
                        color = Color(0xFF9CA3AF),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filtered, key = { it.id }) { user ->
                        UserRow(
                            user = user,
                            onEdit = { userToEdit = it },
                            onDelete = { userToDelete = it }
                        )
                    }
                }
            }
        }

        if (userToEdit != null) {
            EditUserDialog(
                user = userToEdit!!,
                onDismiss = { userToEdit = null },
                onConfirm = { id, name, lastName, phone ->
                    usersViewModel.updateUser(
                        id = id,
                        name = name,
                        lastName = lastName,
                        phone = phone.ifBlank { null }
                    )
                    Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                    userToEdit = null
                }
            )
        }

        if (userToDelete != null) {
            AlertDialog(
                onDismissRequest = { userToDelete = null },
                title = { Text("Eliminar usuario", color = Color.White) },
                text = {
                    Text(
                        "¿Seguro que deseas eliminar a " +
                                "${userToDelete!!.name} ${userToDelete!!.lastName}?",
                        color = Color(0xFFCBD5F5)
                    )
                },
                containerColor = Color(0xFF1D283A),
                confirmButton = {
                    TextButton(
                        onClick = {
                            usersViewModel.deleteUser(userToDelete!!.id)
                            Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                            userToDelete = null
                        }
                    ) {
                        Text("Eliminar", color = Color(0xFFF87171))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { userToDelete = null }) {
                        Text("Cancelar", color = Color(0xFF9CA3AF))
                    }
                }
            )
        }
    }
}

@Composable
private fun UserRow(
    user: ManagedUser,
    onEdit: (ManagedUser) -> Unit,
    onDelete: (ManagedUser) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0B1120)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit(user) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${user.name} ${user.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF9CA3AF)
                )
                user.phone?.let { phone ->
                    if (phone.isNotBlank()) {
                        Text(
                            text = "Tel: $phone",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }

            IconButton(onClick = { onEdit(user) }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color(0xFF60A5FA)
                )
            }

            IconButton(onClick = { onDelete(user) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color(0xFFF87171)
                )
            }
        }
    }
}

@Composable
private fun EditUserDialog(
    user: ManagedUser,
    onDismiss: () -> Unit,
    onConfirm: (id: Int, name: String, lastName: String, phone: String) -> Unit
) {
    var name by rememberSaveable(user.id) { mutableStateOf(user.name) }
    var lastName by rememberSaveable(user.id) { mutableStateOf(user.lastName) }
    var phone by rememberSaveable(user.id) { mutableStateOf(user.phone ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar usuario", color = Color.White) },
        containerColor = Color(0xFF1D283A),
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF0B1120),
                        unfocusedContainerColor = Color(0xFF0B1120),
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF374151),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFF60A5FA),
                        unfocusedLabelColor = Color(0xFF9CA3AF),
                        cursorColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF0B1120),
                        unfocusedContainerColor = Color(0xFF0B1120),
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF374151),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFF60A5FA),
                        unfocusedLabelColor = Color(0xFF9CA3AF),
                        cursorColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it.filter { c -> c.isDigit() } },
                    label = { Text("Teléfono (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF0B1120),
                        unfocusedContainerColor = Color(0xFF0B1120),
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF374151),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFF60A5FA),
                        unfocusedLabelColor = Color(0xFF9CA3AF),
                        cursorColor = Color.White
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(user.id, name.trim(), lastName.trim(), phone.trim())
                }
            ) {
                Text("Guardar", color = Color(0xFF60A5FA))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Color(0xFF9CA3AF))
            }
        }
    )
}
