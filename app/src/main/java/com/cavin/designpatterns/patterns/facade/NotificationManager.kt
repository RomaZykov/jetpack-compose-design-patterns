package com.cavin.designpatterns.patterns.facade

class NotificationManager {
    fun showNotification(track: String) {
        println("Showing notification: Now playing $track")
    }

    fun clearNotification() {
        println("Clearing notification")
    }
}