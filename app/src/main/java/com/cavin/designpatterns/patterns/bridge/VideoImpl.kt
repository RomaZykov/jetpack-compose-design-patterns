package com.cavin.designpatterns.patterns.bridge

class YoutubeVideo(processor: VideoProcessor) : Video(processor) {
    override fun play(videoFile: String) {
        process(videoFile)
        println("Playing $videoFile on YouTube.")
    }
}


class NetflixVideo(processor: VideoProcessor) : Video(processor) {
    override fun play(videoFile: String) {
        process(videoFile)
        println("Playing $videoFile on Netflix.")
    }
}


class AmazonPrimeVideo(processor: VideoProcessor) : Video(processor) {
    override fun play(videoFile: String) {
        process(videoFile)
        println("Playing $videoFile on Amazon Prime.")
    }
}