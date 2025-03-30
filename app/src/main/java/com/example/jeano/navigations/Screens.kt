package com.example.jeano.navigations

sealed class Screens(val route: String) {
    object LandingScreen: Screens(route = "landing_screen")
    object JeanoChatScreen: Screens(route = "jeano_chat_screen")
}