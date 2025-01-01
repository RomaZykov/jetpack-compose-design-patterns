package com.cavin.designpatterns.patterns.command

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

class UpdateTextCommand(
    private val controller: TextFieldValue,
    private val onTextChanged: (TextFieldValue) -> Unit,
    private val newText: String
) : TextCommand {
    private val oldText = controller.text

    override fun execute() {
        val updatedText = TextFieldValue(
            text = newText,
            selection = TextRange(newText.length) // Move cursor to the end
        )
        onTextChanged(updatedText)
    }

    override fun undo() {
        val updatedText = TextFieldValue(
            text = oldText,
            selection = TextRange(oldText.length) // Move cursor to the end
        )
        onTextChanged(updatedText)
    }
}