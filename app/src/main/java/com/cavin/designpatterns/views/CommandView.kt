package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.command.TextEditorController
import com.cavin.designpatterns.patterns.command.UpdateTextCommand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandPatternView() {
    val textFieldState = remember { mutableStateOf(TextFieldValue(text = "")) }
    val textEditorController = remember { TextEditorController() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Command Pattern in Jetpack Compose") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = textFieldState.value,
                onValueChange = { newValue ->
                    textEditorController.executeCommand(
                        UpdateTextCommand(
                            controller = textFieldState.value,
                            onTextChanged = { updatedValue -> textFieldState.value = updatedValue },
                            newText = newValue.text
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { textEditorController.undo() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Undo")
                }

                Button(
                    onClick = { textEditorController.redo() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Redo")
                }
            }
        }
    }
}
