package com.cavin.designpatterns.patterns.abstract_factory

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class LightThemeButton : ThemeButton {
    @Composable
    override fun Create(onClick: () -> Unit, content: @Composable () -> Unit) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            content()
        }
    }
}

class LightThemeCard : ThemeCard {
    @Composable
    override fun Create(content: @Composable () -> Unit) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
            modifier = Modifier
                .padding(8.dp)
                .size(200.dp)
        ) {
            content()
        }
    }
}

class DarkThemeButton : ThemeButton {
    @Composable
    override fun Create(onClick: () -> Unit, content: @Composable () -> Unit) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            content()
        }
    }
}

class DarkThemeCard : ThemeCard {
    @Composable
    override fun Create(content: @Composable () -> Unit) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            modifier = Modifier
                .padding(8.dp)
                .size(200.dp)
        ) {
            content()
        }
    }
}
