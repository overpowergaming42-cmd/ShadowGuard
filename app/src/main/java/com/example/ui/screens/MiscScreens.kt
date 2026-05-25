package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.components.TopBar
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@Composable
fun AIAssistantScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val chatLog = remember { mutableStateListOf<Pair<String, Boolean>>(
        "Hello, I am your Security AI. How can I assist you with your device's protection today?" to false
    ) }

    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopBar("Security AI", onBack = { navController.popBackStack() })
            
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(chatLog.size) { i ->
                    val (msg, isUser) = chatLog[i]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                    ) {
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            Row(verticalAlignment = Alignment.Top) {
                                if (!isUser) {
                                    Icon(Icons.Default.SmartToy, null, tint = NeonBlue, modifier = Modifier.size(24.dp))
                                    Spacer(Modifier.width(8.dp))
                                }
                                Text(msg, color = Color.White)
                            }
                        }
                    }
                }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Ask about threats...", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonBlue,
                        unfocusedBorderColor = DarkSurface,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (query.isNotBlank()) {
                            chatLog.add(query to true)
                            // Simulated AI Response
                            chatLog.add("I have analyzed your request. Your system is currently secured. I recommend running a full file encryption scan for maximum privacy." to false)
                            query = ""
                        }
                    },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Send, null, tint = NeonBlue)
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopBar("Settings", onBack = { navController.popBackStack() })
            
            Text("General", color = NeonBlue, modifier = Modifier.padding(vertical = 16.dp))
            
            val options = listOf(
                "Change Master PIN",
                "Biometric Unlock",
                "Theme Settings",
                "Fake Screen Modes",
                "Backup Vault",
                "About ShadowGuard"
            )
            
            options.forEach { opt ->
                TextButton(onClick = { }, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp)) {
                    Text(opt, color = Color.White, modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.bodyLarge)
                }
                HorizontalDivider(color = DarkSurface)
            }
        }
    }
}
