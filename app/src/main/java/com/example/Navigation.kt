package com.example

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.viewmodels.SecurityViewModel
import com.example.viewmodels.SecurityViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val securityViewModel: SecurityViewModel = viewModel(factory = SecurityViewModelFactory(context))

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController, securityViewModel) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("app_lock") { AppLockScreen(navController) }
        composable("vault") { VaultScreen(navController) }
        composable("intruder") { IntruderReportsScreen(navController, securityViewModel) }
        composable("anti_theft") { AntiTheftScreen(navController) }
        composable("privacy") { PrivacyMonitorScreen(navController) }
        composable("ai_assistant") { AIAssistantScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}

