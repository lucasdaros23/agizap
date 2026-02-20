package com.example.agizap

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.example.agizap.modules.navigation.NavGraph
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.ui.theme.AgizapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Disable dynamicColor to use the custom color scheme defined in ui.theme
            AgizapTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController: NavHostController = rememberNavController()
                    val context = LocalContext.current
                    val isLogged = PreferencesManager(context).isLoggedIn()
                    NavGraph(
                        navController = navController,
                        isLogged = isLogged
                    )
                }
            }
        }
    }
}
