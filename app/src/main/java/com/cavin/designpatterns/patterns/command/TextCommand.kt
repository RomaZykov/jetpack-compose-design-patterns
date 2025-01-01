package com.cavin.designpatterns.patterns.command

interface TextCommand {
    fun execute()
    fun undo()
}