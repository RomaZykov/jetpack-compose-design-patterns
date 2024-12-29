package com.cavin.designpatterns.patterns.adapter

data class WeatherPresentation(
    val tempDisplay: String,
    val humidityDisplay: String,
    val windDisplay: String,
    val weatherIcon: Int
)
