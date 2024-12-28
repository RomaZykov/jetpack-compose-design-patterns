package com.cavin.designpatterns.patterns.abstract_factory

interface ThemeComponentsFactory {
    fun createButton(): ThemeButton
    fun createCard(): ThemeCard
}

class LightThemeFactory : ThemeComponentsFactory {
    override fun createButton(): ThemeButton = LightThemeButton()
    override fun createCard(): ThemeCard = LightThemeCard()
}

class DarkThemeFactory : ThemeComponentsFactory {
    override fun createButton(): ThemeButton = DarkThemeButton()
    override fun createCard(): ThemeCard = DarkThemeCard()
}