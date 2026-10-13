package com.example.weatherandroidtif24.data

import com.example.weatherandroidtif24.data.api.GeocodingResponse
import com.example.weatherandroidtif24.data.api.httpClient
import com.example.weatherandroidtif24.model.City
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiCityRepository(
    private val client: HttpClient = httpClient,
) : CityRepository {

    override suspend fun searchCities(query: String): List<City> {
        val response: GeocodingResponse =
            client.get("https://geocoding-api.open-meteo.com/v1/search") {
                parameter("name", query)
                parameter("count", 5)
                parameter("language", "de")
            }.body()
        return response.results.map { City(it.name, it.latitude, it.longitude) }
    }
}
