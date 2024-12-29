package com.cavin.designpatterns.patterns.facade

class AnalyticsManager {
    fun logEvent(event: String) {
        println("Logging event: $event")
    }
}