package com.example.jeano.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jeano.view.JeanoChatScreen
import com.example.jeano.view.LandingScreen

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
    }
}