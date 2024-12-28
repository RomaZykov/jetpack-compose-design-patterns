package com.cavin.designpatterns.patterns.singleton

object ThemeConfig {
    private var darkModeEnabled: Boolean = false

    fun isDarkModeEnabled(): Boolean = darkModeEnabled

    fun toggleDarkMode() {
        darkModeEnabled = !darkModeEnabled
    }
}