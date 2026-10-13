package com.example.weatherandroidtif24.data.api

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponse(
    val results: List<GeocodingResultDto> = emptyList(), // fehlt im JSON, wenn nichts gefunden wurde
)

@Serializable
data class GeocodingResultDto(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
