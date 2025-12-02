package com.example.evaluacion.auth

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class ManagedUser(
    val id: Int,
    var name: String,
    var lastName: String,
    var email: String,
    var phone: String?
)

class UsersViewModel : ViewModel() {

    private val _users = mutableStateListOf<ManagedUser>()
    val users: List<ManagedUser> get() = _users

    private var nextId = 1

    fun addUser(
        name: String,
        lastName: String,
        email: String,
        phone: String?
    ) {
        val existing = _users.firstOrNull { it.email.equals(email, ignoreCase = true) }
        if (existing == null) {
            _users.add(
                ManagedUser(
                    id = nextId++,
                    name = name,
                    lastName = lastName,
                    email = email,
                    phone = phone
                )
            )
        }
    }

    fun updateUser(
        id: Int,
        name: String,
        lastName: String,
        phone: String?
    ) {
        val index = _users.indexOfFirst { it.id == id }
        if (index != -1) {
            val old = _users[index]
            _users[index] = old.copy(
                name = name,
                lastName = lastName,
                phone = phone
            )
        }
    }

    fun deleteUser(id: Int) {
        _users.removeAll { it.id == id }
    }
}
