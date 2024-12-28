package com.cavin.designpatterns.patterns.factory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalCardFactory = staticCompositionLocalOf<NewsCardFactory> {
    SimpleCardFactory() // Default
}

enum class CardType { SIMPLE, RICH }

@Composable
fun CardFactoryProvider(
    cardType: CardType = CardType.SIMPLE,
    content: @Composable () -> Unit
) {
    val factory = when (cardType) {
        CardType.SIMPLE -> SimpleCardFactory()
        CardType.RICH -> RichCardFactory()
    }
    CompositionLocalProvider(LocalCardFactory provides factory) {
        content()
    }
}

