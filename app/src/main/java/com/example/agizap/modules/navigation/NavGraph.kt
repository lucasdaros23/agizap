package com.example.agizap.modules.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.agizap.modules.feature.home.HomeViewModel
import com.example.agizap.modules.feature.home.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agizap.modules.auth.AuthViewModel
import com.example.agizap.modules.feature.login.LoginScreen
import com.example.agizap.modules.feature.login.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel,
    authViewModel: AuthViewModel,
){
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ){
        composable(Routes.HOME){
            HomeScreen(homeViewModel, navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(loginViewModel, navController)
        }
    }
}