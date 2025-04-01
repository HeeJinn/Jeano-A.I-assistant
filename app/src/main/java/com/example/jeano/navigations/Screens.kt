package com.example.jeano.navigations

sealed class Screens(val route: String) {
    object LandingScreen: Screens(route = "landing_screen")
    object JeanoChatScreen: Screens(route = "jeano_chat_screen")
    object BreaihChatScreen: Screens(route = "breaih_chat_screen")
    object LeeChatScreen: Screens(route = "lee_chat_screen")
    object DevelopersScreen: Screens(route = "developers_screen")
}