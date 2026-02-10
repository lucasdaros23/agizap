package com.example.agizap.modules.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.agizap.modules.feature.home.HomeViewModel
import com.example.agizap.modules.feature.home.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
){
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ){
        composable(Routes.HOME){
            HomeScreen(homeViewModel, navController)
        }
    }
}