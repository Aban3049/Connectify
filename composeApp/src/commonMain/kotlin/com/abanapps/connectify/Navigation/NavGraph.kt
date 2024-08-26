package com.abanapps.connectify.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abanapps.connectify.Screens.AddContactScreen
import com.abanapps.connectify.Screens.HomeScreen
import com.abanapps.connectify.viewModel.ViewModelApp

@Composable
fun NavGraph(viewModelApp: ViewModelApp){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen){
        composable<Routes.AddContactScreen> {
            AddContactScreen(viewModelApp)
        }

        composable<Routes.HomeScreen> {
            HomeScreen(navController,viewModelApp)
        }

    }

}