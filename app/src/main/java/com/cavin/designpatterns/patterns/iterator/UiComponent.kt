package com.cavin.designpatterns.patterns.iterator

import androidx.compose.runtime.Composable

sealed interface UIComponent {
    @Composable
    fun interpret()
}