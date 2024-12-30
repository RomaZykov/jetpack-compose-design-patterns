package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cavin.designpatterns.patterns.proxy.WeatherService
import com.cavin.designpatterns.patterns.proxy.WeatherServiceProxy
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProxyView() {
    val weatherService: WeatherService = remember { WeatherServiceProxy() }

    val weatherData = remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        delay(3000)
        weatherData.value = getWeatherFiveTimes(weatherService)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Weather App") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = weatherData.value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

suspend fun getWeatherFiveTimes(service: WeatherService): String {
    val results = mutableListOf<String>()
    repeat(5) {
        val data = service.getWeatherData()
        results.add(data)
    }
    return results.joinToString("\n")
}