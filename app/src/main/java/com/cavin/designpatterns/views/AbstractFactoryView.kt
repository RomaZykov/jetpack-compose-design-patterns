package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cavin.designpatterns.patterns.abstract_factory.DarkThemeFactory
import com.cavin.designpatterns.patterns.abstract_factory.LightThemeFactory
import com.cavin.designpatterns.patterns.abstract_factory.ThemeComponentsFactory

@Composable
fun AbstractFactoryView(modifier: Modifier = Modifier) {
    var isDarkTheme by remember { mutableStateOf(false) }
    val themeFactory: ThemeComponentsFactory = if (isDarkTheme) {
        DarkThemeFactory()
    } else {
        LightThemeFactory()
    }
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            themeFactory.createButton().Create(
                onClick = { isDarkTheme = !isDarkTheme }
            ) {
                Text("Switch Theme")
            }

            themeFactory.createCard().Create {
                Text("This is a themed card")
            }
        }
    }
}

