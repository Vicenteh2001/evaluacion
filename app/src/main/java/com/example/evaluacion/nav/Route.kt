package com.example.evaluacion.nav

sealed class Route(val route: String) {
    object Splash : Route("splash")
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")

    object ForgotEmail : Route("forgot_email")
    object ForgotCode : Route("forgot_code")
    object ForgotReset : Route("forgot_reset")

    object UsersMenu : Route("users_menu")
    object UserCreate : Route("user_create")
    object UsersList : Route("users_list")

    object Sensors : Route("sensors")
    object Developer : Route("developer")
}
