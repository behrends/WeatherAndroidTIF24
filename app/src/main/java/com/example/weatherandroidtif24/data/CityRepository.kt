package com.example.weatherandroidtif24.data

import com.example.weatherandroidtif24.model.City

interface CityRepository {
    suspend fun searchCities(query: String): List<City>
}
