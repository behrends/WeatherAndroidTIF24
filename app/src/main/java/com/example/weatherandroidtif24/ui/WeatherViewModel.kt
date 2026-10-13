package com.example.weatherandroidtif24.ui

import androidx.lifecycle.ViewModel
import com.example.weatherandroidtif24.data.fakeCities
import com.example.weatherandroidtif24.data.fakeCurrentWeather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherUiState>(
        WeatherUiState.Success(
            city = fakeCities.first(),
            weather = fakeCurrentWeather.getValue(fakeCities.first().name),
        )
    )
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
}
