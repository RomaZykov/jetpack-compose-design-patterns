package com.cavin.designpatterns.patterns.iterator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

class ConcreteExpressionText(private val text: String) : UIComponent {
    @Composable
    override fun interpret() {
        Text(text = text, style = TextStyle(fontSize = 20.sp))
    }
}
