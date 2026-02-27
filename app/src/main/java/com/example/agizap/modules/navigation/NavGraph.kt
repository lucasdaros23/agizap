package com.example.agizap.modules.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.agizap.modules.feature.home.HomeViewModel
import com.example.agizap.modules.feature.home.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.agizap.modules.feature.chat.ChatScreen
import com.example.agizap.modules.feature.chat.ChatViewModel
import com.example.agizap.modules.feature.chatinfo.ChatInfoScreen
import com.example.agizap.modules.feature.chatinfo.ChatInfoViewModel
import com.example.agizap.modules.feature.edit.EditScreen
import com.example.agizap.modules.feature.edit.EditViewModel
import com.example.agizap.modules.feature.login.LoginScreen
import com.example.agizap.modules.feature.login.LoginViewModel
import com.example.agizap.modules.feature.register.RegisterScreen
import com.example.agizap.modules.feature.register.RegisterViewModel
import com.example.agizap.modules.preferences.PreferencesManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    isLogged: Boolean
){
    NavHost(
        navController = navController,
        startDestination = if (isLogged) Routes.HOME else Routes.LOGIN
    ){
        composable(Routes.HOME){
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(homeViewModel, navController)
        }
        composable(Routes.LOGIN) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(loginViewModel, navController)
        }
        composable(Routes.REGISTER){
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(registerViewModel, navController)
        }
        composable(
            route = Routes.INFO,
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val chatInfoViewModel: ChatInfoViewModel = hiltViewModel()
            ChatInfoScreen(
                viewModel = chatInfoViewModel,
                chatId = chatId,
                navController = navController
            )
        }
        composable(
            route = Routes.CHAT,
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val chatViewModel: ChatViewModel = hiltViewModel()
            ChatScreen(
                viewModel = chatViewModel,
                chatId = chatId,
                navController = navController
            )
        }
        composable(Routes.EDIT) {
            val editViewModel: EditViewModel = hiltViewModel()
            EditScreen(
                editViewModel,
                navController
            )
        }
    }
}