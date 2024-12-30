package com.cavin.designpatterns.patterns.flyweight

import androidx.compose.ui.graphics.vector.ImageVector

object IconFactory {
    private val icons = mutableMapOf<ImageVector, IconFlyweight>()

    fun getIcon(icon: ImageVector): IconFlyweight {
        return icons.getOrPut(icon) { IconFlyweight(icon) }
    }
}