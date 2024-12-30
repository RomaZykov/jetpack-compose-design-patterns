package com.cavin.designpatterns.patterns.proxy

class WeatherApiService : WeatherService {
    override suspend fun getWeatherData(): String {
        return "Sunny, 25°C"
    }
}