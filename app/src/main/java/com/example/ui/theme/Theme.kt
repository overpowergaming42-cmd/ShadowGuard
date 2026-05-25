package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = NeonBlue,
    secondary = ElectricCyan,
    tertiary = CyberAccent,
    background = CyberGray,
    surface = DarkSurface,
    surfaceVariant = DarkSurface,
    onPrimary = CyberGray,
    onSecondary = CyberGray,
    onTertiary = CyberGray,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = LightGray,
    error = DangerRed,
    onError = Color.White
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Force dark theme for cyber feel
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(colorScheme = DarkColorScheme, typography = Typography, content = content)
}
