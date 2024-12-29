package com.cavin.designpatterns.patterns.adapter

import com.cavin.designpatterns.R

class WeatherDataAdapter {
    fun adapt(data: WeatherData): WeatherPresentation {
        return WeatherPresentation(
            tempDisplay = "${data.temperature}°C",
            humidityDisplay = "${data.humidity}%",
            windDisplay = "${data.windSpeed} m/s",
            weatherIcon = when (data.condition.lowercase()) {
                "sunny" -> R.drawable.logo
                "cloudy" -> R.drawable.ic_launcher_background
                "rainy" -> R.drawable.ic_launcher_foreground
                else -> R.drawable.logo
            }
        )
    }
}