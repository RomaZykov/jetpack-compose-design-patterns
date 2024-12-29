package com.cavin.designpatterns.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.adapter.WeatherData
import com.cavin.designpatterns.patterns.adapter.WeatherDataAdapter

@Composable
fun AdaptorView() {
    val dummyWeatherData = remember {
        WeatherData(
            temperature = 25.3f,
            humidity = 65.0f,
            windSpeed = 12.0f,
            condition = "Sunny"
        )
    }
    val adapter = remember { WeatherDataAdapter() }
    val presentation = adapter.adapt(dummyWeatherData)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Temperature: ${presentation.tempDisplay}")
        Text("Humidity: ${presentation.humidityDisplay}")
        Text("Wind Speed: ${presentation.windDisplay}")
        Image(
            painter = painterResource(presentation.weatherIcon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(50.dp)
        )
    }
}