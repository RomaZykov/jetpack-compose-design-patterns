package com.cavin.designpatterns.patterns.iterator

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

class ConcreteExpressionImage(private val url: String) : UIComponent {
    @Composable
    override fun interpret() {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}