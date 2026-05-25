package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*

@Composable
fun CyberButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isOutlined: Boolean = false
) {
    val shape = RoundedCornerShape(12.dp)
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(56.dp),
            shape = shape,
            border = androidx.compose.foundation.BorderStroke(2.dp, NeonBlue),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonBlue)
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text, fontWeight = FontWeight.Bold)
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier
                .height(56.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(ElectricCyan, NeonBlue)),
                    shape = shape
                ),
            shape = shape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = CyberGray)
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    var baseModifier = modifier
        .clip(RoundedCornerShape(16.dp))
        .background(DarkSurface.copy(alpha = 0.6f))
        .border(1.dp, NeonBlue.copy(alpha = 0.3f), RoundedCornerShape(16.dp))

    if (onClick != null) {
        baseModifier = baseModifier.clickable { onClick() }
    }

    Box(
        modifier = baseModifier.padding(16.dp),
        content = content
    )
}

@Composable
fun TopBar(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = NeonBlue
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}
