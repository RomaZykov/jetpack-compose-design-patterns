package com.cavin.designpatterns.patterns.factory

import androidx.compose.runtime.Composable

interface NewsCardFactory {
    @Composable
    fun CreateCard(newsItem: NewsItem)
}



