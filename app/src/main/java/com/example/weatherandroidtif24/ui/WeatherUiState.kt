package com.example.weatherandroidtif24.ui

import com.example.weatherandroidtif24.model.City
import com.example.weatherandroidtif24.model.CurrentWeather

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val city: City, val weather: CurrentWeather) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}
