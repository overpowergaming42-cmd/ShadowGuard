package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        containerColor = CyberGray,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("ai_assistant") },
                containerColor = NeonBlue,
                contentColor = CyberGray
            ) {
                Icon(Icons.Default.SmartToy, "AI Assistant")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "SHADOWGUARD",
                    style = MaterialTheme.typography.titleLarge,
                    color = NeonBlue,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = androidx.compose.ui.unit.TextUnit(2f, androidx.compose.ui.unit.TextUnitType.Sp)
                )
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, "Settings", tint = Color.White)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Security Score Circle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(160.dp)) {
                    drawArc(
                        color = DarkSurface,
                        startAngle = 135f,
                        sweepAngle = 270f,
                        useCenter = false,
                        style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = SafeGreen,
                        startAngle = 135f,
                        sweepAngle = 270f * 0.98f, // 98% score
                        useCenter = false,
                        style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "98",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Excellent",
                        style = MaterialTheme.typography.titleMedium,
                        color = SafeGreen
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Protection Modules",
                style = MaterialTheme.typography.titleMedium,
                color = LightGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            val modules = listOf(
                ModuleItem("App Lock", Icons.Default.Lock, "app_lock", NeonBlue),
                ModuleItem("Secure Vault", Icons.Default.FolderSpecial, "vault", ElectricCyan),
                ModuleItem("Intruder Alerts", Icons.Default.Warning, "intruder", OrangeWarning),
                ModuleItem("Anti Theft", Icons.Default.NoCell, "anti_theft", DangerRed),
                ModuleItem("Privacy Monitor", Icons.Default.VisibilityOff, "privacy", SafeGreen),
                ModuleItem("Emergency SOS", Icons.Default.Sos, "settings", DangerRed)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(modules) { module ->
                    GlassCard(
                        onClick = { navController.navigate(module.route) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(module.color.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(module.icon, contentDescription = module.title, tint = module.color)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                module.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

data class ModuleItem(val title: String, val icon: ImageVector, val route: String, val color: Color)
