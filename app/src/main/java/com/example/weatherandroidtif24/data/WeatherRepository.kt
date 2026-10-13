package com.example.weatherandroidtif24.data

import com.example.weatherandroidtif24.model.City
import com.example.weatherandroidtif24.model.CurrentWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(city: City): CurrentWeather
}
