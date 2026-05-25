package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.components.TopBar
import com.example.ui.theme.CyberGray
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.NeonBlue

@Composable
fun AppLockScreen(navController: NavController) {
    Scaffold(containerColor = CyberGray) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TopBar("App Lock", onBack = { navController.popBackStack() })
            
            var masterToggle by remember { mutableStateOf(true) }
            
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Enable App Lock", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Text("Protect selected apps with PIN", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = masterToggle,
                        onCheckedChange = { masterToggle = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = NeonBlue, checkedTrackColor = NeonBlue.copy(alpha=0.5f))
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Installed Apps", color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val mockApps = listOf("WhatsApp", "Photos", "Messages", "Gmail", "Instagram", "Settings")
                items(mockApps.size) { index ->
                    AppItem(mockApps[index], index % 2 == 0)
                }
            }
        }
    }
}

@Composable
fun AppItem(name: String, initiallyLocked: Boolean) {
    var isLocked by remember { mutableStateOf(initiallyLocked) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Mock App Icon
            Surface(
                modifier = Modifier.size(40.dp),
                shape = androidx.compose.foundation.shape.CircleShape,
                color = DarkSurface
            ) {}
            Spacer(modifier = Modifier.width(16.dp))
            Text(name, color = Color.White)
        }
        IconButton(onClick = { isLocked = !isLocked }) {
            Icon(
                imageVector = if (isLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                contentDescription = null,
                tint = if (isLocked) NeonBlue else Color.Gray
            )
        }
    }
}
