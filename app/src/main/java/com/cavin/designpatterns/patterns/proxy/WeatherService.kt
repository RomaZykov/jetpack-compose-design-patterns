package com.cavin.designpatterns.patterns.proxy

interface WeatherService {
    suspend fun getWeatherData(): String
}