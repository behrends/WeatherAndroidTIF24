package com.example.weatherandroidtif24.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherandroidtif24.data.fakeCities
import com.example.weatherandroidtif24.data.fakeCurrentWeather
import com.example.weatherandroidtif24.model.City
import com.example.weatherandroidtif24.model.CurrentWeather
import com.example.weatherandroidtif24.ui.theme.WeatherAndroidTIF24Theme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(city: City, weather: CurrentWeather, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = city.name) }) },
    ) { innerPadding ->
        CurrentWeatherCard(
            city = city,
            weather = weather,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun CurrentWeatherCard(city: City, weather: CurrentWeather, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFBBDEFB), RoundedCornerShape(24.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = city.name, fontSize = 28.sp)
            Text(text = weather.condition.emoji(), fontSize = 72.sp)
            Text(text = "${weather.temperature.roundToInt()} °C", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Wind: ${weather.windSpeed.roundToInt()} km/h")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = weather.condition.label())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    val city = fakeCities.first()
    WeatherAndroidTIF24Theme {
        WeatherScreen(city = city, weather = fakeCurrentWeather.getValue(city.name))
    }
}
