package com.cavin.designpatterns.patterns.composite

import androidx.compose.runtime.Composable

interface CartItem {
    fun getName(): String

    fun getPrice(): Double

    @Composable
    fun Render()
}