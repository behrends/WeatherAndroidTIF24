package com.example.weatherandroidtif24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.weatherandroidtif24.ui.WeatherScreen
import com.example.weatherandroidtif24.ui.theme.WeatherAndroidTIF24Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAndroidTIF24Theme {
                WeatherScreen()
            }
        }
    }
}
