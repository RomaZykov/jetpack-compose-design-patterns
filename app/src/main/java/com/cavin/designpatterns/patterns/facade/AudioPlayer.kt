package com.cavin.designpatterns.patterns.facade

class AudioPlayer {
    fun play(track: String) {
        println("Playing track: $track")
    }

    fun pause() {
        println("Audio paused")
    }

    fun stop() {
        println("Audio stopped")
    }
}