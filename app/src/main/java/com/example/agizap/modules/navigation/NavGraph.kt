package com.example.agizap.modules.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.agizap.modules.feature.home.HomeViewModel
import com.example.agizap.modules.feature.home.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.agizap.modules.feature.chat.ChatScreen
import com.example.agizap.modules.feature.chat.ChatViewModel
import com.example.agizap.modules.feature.login.LoginScreen
import com.example.agizap.modules.feature.login.LoginViewModel
import com.example.agizap.modules.feature.register.RegisterScreen
import com.example.agizap.modules.feature.register.RegisterViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    chatViewModel: ChatViewModel
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
        composable(Routes.REGISTER){
            RegisterScreen(registerViewModel, navController)
        }
        composable(
            route = Routes.CHAT,
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatScreen(
                viewModel = chatViewModel,
                chatId = chatId,
                navController = navController

            )
        }
    }
}