package com.cavin.designpatterns.patterns.flyweight

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

class IconFlyweight(private val icon: ImageVector) : Flyweight {

    @Composable
    override fun render(color: Color, size: Dp) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(size),
        )
    }
}