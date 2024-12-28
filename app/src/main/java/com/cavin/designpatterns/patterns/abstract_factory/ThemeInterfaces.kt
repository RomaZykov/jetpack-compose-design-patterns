package com.cavin.designpatterns.patterns.abstract_factory

import androidx.compose.runtime.Composable

interface ThemeButton {
    @Composable
    fun Create(onClick: () -> Unit, content: @Composable () -> Unit)
}

interface ThemeCard {
    @Composable
    fun Create(content: @Composable () -> Unit)
}