package com.example.jeano.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jeano.view.BreiahChatScreen
import com.example.jeano.view.DevelopersScreen
import com.example.jeano.view.JeanoChatScreen
import com.example.jeano.view.LandingScreen
import com.example.jeano.view.LeeChatScreen

@Composable
fun MainNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.LandingScreen.route
    ){
        composable(route = Screens.LandingScreen.route) {
            LandingScreen(navController)
        }
        composable(route = Screens.JeanoChatScreen.route) {
            JeanoChatScreen(navController)
        }
        composable(route = Screens.BreaihChatScreen.route) {
            BreiahChatScreen(navController)
        }
        composable(route = Screens.LeeChatScreen.route) {
            LeeChatScreen(navController)
        }
        composable(route = Screens.DevelopersScreen.route) {
            DevelopersScreen(navController)
        }


    }
}