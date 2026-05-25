package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.components.TopBar
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@Composable
fun VaultScreen(navController: NavController) {
    var isUnlocked by remember { mutableStateOf(false) }

    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopBar("Secure Vault", onBack = { navController.popBackStack() })

            if (!isUnlocked) {
                // Pin Entry Mode
                Column(Modifier.fillMaxWidth().padding(top = 40.dp), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Lock, null, tint = ElectricCyan, modifier = Modifier.size(80.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("Vault is Locked", color = Color.White, style = MaterialTheme.typography.titleLarge)
                    Text("Enter secondary PIN", color = Color.Gray)
                    Spacer(Modifier.height(32.dp))
                    
                    OutlinedTextField(
                        value = "", onValueChange = {},
                        placeholder = { Text("PIN", color=Color.Gray) },
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonBlue)
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { isUnlocked = true },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = CyberGray)
                    ) {
                        Text("UNLOCK")
                    }
                }
            } else {
                Spacer(Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val tabs = listOf("Photos" to Icons.Default.Image, "Videos" to Icons.Default.Videocam, "Docs" to Icons.Default.Description, "Passwords" to Icons.Default.Key)
                    items(tabs.size) { i ->
                        GlassCard {
                            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Icon(tabs[i].second, null, tint = ElectricCyan, modifier = Modifier.size(40.dp))
                                Spacer(Modifier.height(8.dp))
                                Text(tabs[i].first, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IntruderReportsScreen(navController: NavController, securityViewModel: com.example.viewmodels.SecurityViewModel? = null) {
    val logs by securityViewModel?.intruderLogs?.collectAsStateWithLifecycle(initialValue = emptyList()) ?: mutableStateOf(emptyList())

    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopBar("Intruder Alerts", onBack = { navController.popBackStack() })
            Spacer(Modifier.height(16.dp))
            
            if (logs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No intruders detected.", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(logs.size) { i ->
                        val log = logs[i]
                        val format = java.text.SimpleDateFormat("MMM dd, hh:mm a", java.util.Locale.getDefault())
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, null, tint = OrangeWarning, modifier = Modifier.size(40.dp))
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text(log.type, color = Color.White, style = MaterialTheme.typography.titleMedium)
                                    Text(format.format(java.util.Date(log.timestamp)), color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AntiTheftScreen(navController: NavController) {
    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopBar("Anti Theft", onBack = { navController.popBackStack() })
            Spacer(Modifier.height(16.dp))
            
            val settings = listOf(
                "SIM Change Alert" to "Lock device if SIM is changed",
                "Remote Wipe" to "Allow remote data deletion",
                "Location Tracking" to "Report location when battery < 10%"
            )
            
            settings.forEach { (title, subtitle) ->
                var checked by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(title, color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Text(subtitle, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = DangerRed, checkedTrackColor = DangerRed.copy(alpha=0.5f))
                    )
                }
            }
        }
    }
}

@Composable
fun PrivacyMonitorScreen(navController: NavController) {
    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopBar("Privacy Monitor", onBack = { navController.popBackStack() })
            Spacer(Modifier.height(16.dp))
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Active Permissions", color = Color.White, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("3 apps using Camera", color = SafeGreen)
                    Text("1 app using Microphone", color = SafeGreen)
                    Text("5 apps using Location", color = OrangeWarning)
                }
            }
        }
    }
}
