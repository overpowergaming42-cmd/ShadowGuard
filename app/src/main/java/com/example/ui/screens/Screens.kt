package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Block
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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun checkIsBangladesh(): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val url = java.net.URL("https://ipapi.co/country/")
            val connection = url.openConnection() as java.net.HttpURLConnection
            connection.connectTimeout = 3000
            connection.readTimeout = 3000
            connection.requestMethod = "GET"
            val countryCode = connection.inputStream.bufferedReader().use { it.readText() }.trim()
            countryCode.equals("BD", ignoreCase = true)
        } catch (e: Exception) {
            // Fallback to Timezone/Locale if network fails
            val timeZone = java.util.TimeZone.getDefault().id
            if (timeZone == "Asia/Dhaka") true
            else java.util.Locale.getDefault().country.equals("BD", ignoreCase = true)
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        val minDelay = async { delay(2000) }
        val isBD = async { checkIsBangladesh() }
        
        minDelay.await()
        val allowed = isBD.await()
        if (allowed) {
            navController.navigate("welcome") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("region_blocked") {
                popUpTo("splash") { inclusive = true }
            }
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
fun RegionBlockedScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberGray),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Icon(
                imageVector = Icons.Default.Block,
                contentDescription = null,
                tint = com.example.ui.theme.DangerRed,
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Access Denied",
                style = MaterialTheme.typography.headlineMedium,
                color = com.example.ui.theme.DangerRed,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "This application is strictly restricted to users within Bangladesh.",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    var accessKey by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current

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
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Access Restricted",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Please enter your exclusive Access Key to continue.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = accessKey,
                onValueChange = { 
                    accessKey = it
                    isError = false
                },
                label = { Text("Access Key") },
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
            Spacer(modifier = Modifier.height(24.dp))
            CyberButton(
                text = "VERIFY KEY",
                onClick = { 
                    if (accessKey == "Sakibvai420@") {
                        navController.navigate("login") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    } else {
                        isError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                intent.data = android.net.Uri.parse("https://api.whatsapp.com/send?phone=+8801839556139")
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // WhatsApp not installed or error
                }
            }) {
                Text(
                    "Get Key via WhatsApp",
                    color = NeonBlue,
                    fontWeight = FontWeight.Bold
                )
            }
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
