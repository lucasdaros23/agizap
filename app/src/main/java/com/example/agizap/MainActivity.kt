package com.example.agizap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.agizap.modules.auth.AuthViewModel
import com.example.agizap.modules.navigation.NavGraph
import com.example.agizap.ui.theme.AgizapTheme
import com.example.agizap.modules.feature.home.HomeViewModel
import com.example.agizap.modules.feature.login.LoginViewModel
import com.example.agizap.modules.feature.register.RegisterViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Disable dynamicColor to use the custom color scheme defined in ui.theme
            AgizapTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController: NavHostController = rememberNavController()
                    val homeViewModel: HomeViewModel = viewModel()
                    val loginViewModel: LoginViewModel = viewModel()
                    val registerViewModel: RegisterViewModel = viewModel()

                    NavGraph(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        loginViewModel = loginViewModel,
                        registerViewModel = registerViewModel
                    )
                }
            }
        }
    }
}
