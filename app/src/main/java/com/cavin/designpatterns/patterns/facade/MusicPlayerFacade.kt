package com.cavin.designpatterns.patterns.facade

class MusicPlayerFacade(
    private val audioPlayer: AudioPlayer,
    private val notificationManager: NotificationManager,
    private val analyticsManager: AnalyticsManager
) {

    fun playTrack(track: String) {
        audioPlayer.play(track)
        notificationManager.showNotification(track)
        analyticsManager.logEvent("Track played: $track")
    }

    fun pauseTrack() {
        audioPlayer.pause()
        analyticsManager.logEvent("Track paused")
    }

    fun stopTrack() {
        audioPlayer.stop()
        notificationManager.clearNotification()
        analyticsManager.logEvent("Track stopped")
    }
}