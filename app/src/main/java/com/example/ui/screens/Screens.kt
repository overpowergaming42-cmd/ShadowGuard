package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.ui.components.CyberButton
import com.example.ui.components.GlassCard
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.CyberGray
import com.example.ui.theme.ElectricCyan
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("welcome") {
            popUpTo("splash") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberGray),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Shield",
                tint = NeonBlue,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "SHADOWGUARD",
                style = MaterialTheme.typography.headlineLarge,
                color = ElectricCyan,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.dp.value.let { androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Sp) }
            )
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = NeonBlue,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Smart Security\nfor Smart Users",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Protect your device with military-grade encryption and AI-powered threat detection.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            CyberButton(
                text = "GET STARTED",
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LoginScreen(navController: NavController, securityViewModel: com.example.viewmodels.SecurityViewModel? = null) {
    var pin by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Welcome Back",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Enter your master PIN to continue",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(48.dp))
            
            OutlinedTextField(
                value = pin,
                onValueChange = { 
                    pin = it
                    isError = false
                },
                label = { Text("Master PIN") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonBlue,
                    unfocusedBorderColor = Color.DarkGray,
                    focusedLabelColor = NeonBlue,
                    errorBorderColor = com.example.ui.theme.DangerRed
                )
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            CyberButton(
                text = "AUTHENTICATE",
                onClick = {
                    if (pin == "1234") {
                        navController.navigate("dashboard") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    } else {
                        isError = true
                        securityViewModel?.logFailedAttempt()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
