package com.example.weatherandroidtif24.ui

import com.example.weatherandroidtif24.model.City

data class CityListUiState(
    val cities: List<City>,
    val searchResults: List<City> = emptyList(),
    val isSearching: Boolean = false,
    val errorMessage: String? = null,
)
