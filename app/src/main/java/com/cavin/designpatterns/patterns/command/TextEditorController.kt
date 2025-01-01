package com.cavin.designpatterns.patterns.command

class TextEditorController {
    private val commandHistory = mutableListOf<TextCommand>()
    private var currentCommandIndex = -1

    fun executeCommand(command: TextCommand) {
        if (currentCommandIndex != commandHistory.size - 1) {
            commandHistory.subList(currentCommandIndex + 1, commandHistory.size).clear()
        }
        commandHistory.add(command)
        currentCommandIndex++
        command.execute()
    }

    fun undo() {
        if (currentCommandIndex >= 0) {
            commandHistory[currentCommandIndex].undo()
            currentCommandIndex--
        }
    }

    fun redo() {
        if (currentCommandIndex < commandHistory.size - 1) {
            currentCommandIndex++
            commandHistory[currentCommandIndex].execute()
        }
    }
}