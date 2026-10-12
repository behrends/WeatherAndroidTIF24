package com.example.weatherandroidtif24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.weatherandroidtif24.data.fakeCities
import com.example.weatherandroidtif24.data.fakeCurrentWeather
import com.example.weatherandroidtif24.ui.WeatherScreen
import com.example.weatherandroidtif24.ui.theme.WeatherAndroidTIF24Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAndroidTIF24Theme {
                val city = fakeCities.first()
                // Abstand zu Statusleiste und Kamera-Aussparung
                WeatherScreen(
                    city = city,
                    weather = fakeCurrentWeather.getValue(city.name),
                    modifier = Modifier.safeDrawingPadding(),
                )
            }
        }
    }
}
