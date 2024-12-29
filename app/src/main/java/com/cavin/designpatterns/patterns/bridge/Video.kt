package com.cavin.designpatterns.patterns.bridge

abstract class Video(private val processor: VideoProcessor) {
    abstract fun play(videoFile: String)

    protected fun process(videoFile: String) {
        processor.process(videoFile)
    }
}