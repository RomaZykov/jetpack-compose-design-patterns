package com.cavin.designpatterns.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.iterator.WidgetParser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterpreterView() {
    var script by remember { mutableStateOf("") }
    val parser = remember { WidgetParser() }
    var expressions by remember { mutableStateOf(parser.parseScript(script)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Interpreter Pattern") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = script,
                    onValueChange = { script = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(onClick = { expressions = parser.parseScript(script) }) {
                    Text("Interpret the Script")
                }
                Spacer(modifier = Modifier.height(16.dp))
                expressions.forEach { expression ->
                    expression.interpret()
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}