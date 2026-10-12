package com.example.weatherandroidtif24.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    Text(text = "Lörrach", modifier = modifier)
    Text(text = "14 °C", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen()
}
