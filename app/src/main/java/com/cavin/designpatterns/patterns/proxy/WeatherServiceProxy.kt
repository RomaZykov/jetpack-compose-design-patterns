package com.cavin.designpatterns.patterns.proxy

class WeatherServiceProxy(private val apiService: WeatherApiService = WeatherApiService()) : WeatherService {
    private var cachedData: String? = null

    override suspend fun getWeatherData(): String {
        return if (cachedData == null) {
            println("Fetching data from API...")
            cachedData = apiService.getWeatherData()
            cachedData!!
        } else {
            println("Returning cached data...")
            cachedData!!
        }
    }
}