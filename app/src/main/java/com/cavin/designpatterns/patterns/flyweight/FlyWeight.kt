package com.cavin.designpatterns.patterns.flyweight

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

interface Flyweight {

    @Composable
    fun render(color: Color, size: Dp)
}