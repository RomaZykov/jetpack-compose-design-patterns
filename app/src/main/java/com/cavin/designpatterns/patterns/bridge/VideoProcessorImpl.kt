package com.cavin.designpatterns.patterns.bridge

class HDProcessor : VideoProcessor {
    override fun process(videoFile: String) {
        println("$videoFile is being processed with HD quality.")
    }
}

class UHD4KProcessor : VideoProcessor {
    override fun process(videoFile: String) {
        println("$videoFile is being processed with UHD 4K quality.")
    }
}

class QUHD8KProcessor : VideoProcessor {
    override fun process(videoFile: String) {
        println("$videoFile is being processed with QUHD 8K quality.")
    }
}