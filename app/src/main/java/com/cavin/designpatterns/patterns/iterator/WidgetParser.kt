package com.cavin.designpatterns.patterns.iterator

class WidgetParser {
    fun parseScript(script: String): List<UIComponent> {
        val expressions = mutableListOf<UIComponent>()
        script.lines().forEach { line ->
            val trimmedLine = line.trim()
            when {
                trimmedLine.startsWith("Text:") -> {
                    val text = trimmedLine.substringAfter("Text:").trim()
                    expressions.add(ConcreteExpressionText(text))
                }
                trimmedLine.startsWith("Image:") -> {
                    val url = trimmedLine.substringAfter("Image:").trim()
                    if (url.startsWith("https://")) {
                        expressions.add(ConcreteExpressionImage(url))
                    }
                }
            }
        }
        return expressions
    }
}