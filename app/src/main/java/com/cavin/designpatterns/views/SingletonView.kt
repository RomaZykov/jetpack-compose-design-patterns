package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cavin.designpatterns.patterns.singleton.ThemeConfig

@Composable
fun SingletonView(modifier: Modifier = Modifier) {
    Scaffold {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ThemeToggleButton()
        }
    }
}


@Composable
fun ThemeToggleButton() {
    var isDarkMode by remember { mutableStateOf(ThemeConfig.isDarkModeEnabled()) }

    Button(onClick = {
        ThemeConfig.toggleDarkMode()
        isDarkMode = ThemeConfig.isDarkModeEnabled()
    }) {
        Text(if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode")
    }
}

