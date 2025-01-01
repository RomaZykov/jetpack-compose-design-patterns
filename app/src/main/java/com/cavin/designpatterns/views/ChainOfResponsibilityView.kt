package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.chain_of_responsibility.ButtonInteractionHandler
import com.cavin.designpatterns.patterns.chain_of_responsibility.FormInteractionHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChainOfResponsibilityView() {
    var message by remember { mutableStateOf("") }

    // Initialize handlers
    val buttonHandler = ButtonInteractionHandler()
    val formHandler = FormInteractionHandler()
    buttonHandler.setNextHandler(formHandler)

    // UI
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Chain of Responsibility in Compose") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    buttonHandler.handleInteraction("buttonClick") { result ->
                        message = result
                    }
                }) {
                    Text("Click Me")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    buttonHandler.handleInteraction("formSubmit") { result ->
                        message = result
                    }
                }) {
                    Text("Submit Form")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    buttonHandler.handleInteraction("unknown") { result ->
                        message = result
                    }
                }) {
                    Text("Unknown")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}
