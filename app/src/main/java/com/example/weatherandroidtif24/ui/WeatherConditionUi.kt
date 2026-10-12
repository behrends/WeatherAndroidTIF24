package com.example.weatherandroidtif24.ui

import com.example.weatherandroidtif24.model.WeatherCondition

fun WeatherCondition.emoji(): String = when (this) {
    WeatherCondition.CLEAR -> "☀️"
    WeatherCondition.PARTLY_CLOUDY -> "⛅"
    WeatherCondition.CLOUDY -> "☁️"
    WeatherCondition.FOG -> "🌫️"
    WeatherCondition.RAIN -> "🌧️"
    WeatherCondition.SNOW -> "❄️"
    WeatherCondition.THUNDERSTORM -> "⛈️"
}

fun WeatherCondition.label(): String = when (this) {
    WeatherCondition.CLEAR -> "Sonnig"
    WeatherCondition.PARTLY_CLOUDY -> "Teilweise bewölkt"
    WeatherCondition.CLOUDY -> "Bewölkt"
    WeatherCondition.FOG -> "Nebel"
    WeatherCondition.RAIN -> "Regen"
    WeatherCondition.SNOW -> "Schnee"
    WeatherCondition.THUNDERSTORM -> "Gewitter"
}
