package com.example.weatherandroidtif24.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherandroidtif24.data.fakeCities
import com.example.weatherandroidtif24.data.fakeCurrentWeather
import com.example.weatherandroidtif24.model.City
import com.example.weatherandroidtif24.model.CurrentWeather
import com.example.weatherandroidtif24.ui.theme.WeatherAndroidTIF24Theme
import kotlin.math.roundToInt

// Stateful: holt den Zustand aus dem ViewModel
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    WeatherContent(uiState = uiState, modifier = modifier)
}

// Stateless: zeigt nur an, was es bekommt (dadurch auch in der Preview nutzbar)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    uiState: WeatherUiState,
    modifier: Modifier = Modifier,
) {
    // UI-Element-State: bleibt im Composable, gehört nicht ins ViewModel
    var useCelsius by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Wetter") },
                actions = {
                    UnitToggle(useCelsius = useCelsius, onToggle = { useCelsius = !useCelsius })
                },
            )
        },
    ) { innerPadding ->
        when (uiState) {
            WeatherUiState.Loading -> CenteredBox(Modifier.padding(innerPadding)) {
                CircularProgressIndicator()
            }

            is WeatherUiState.Success -> CurrentWeatherCard(
                city = uiState.city,
                weather = uiState.weather,
                useCelsius = useCelsius,
                modifier = Modifier.padding(innerPadding),
            )

            is WeatherUiState.Error -> CenteredBox(Modifier.padding(innerPadding)) {
                Text(text = uiState.message)
            }
        }
    }
}

@Composable
fun CenteredBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        content()
    }
}

@Composable
fun UnitToggle(useCelsius: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick = onToggle, modifier = modifier) {
        Text(text = if (useCelsius) "°C" else "°F")
    }
}

@Composable
fun CurrentWeatherCard(
    city: City,
    weather: CurrentWeather,
    useCelsius: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFBBDEFB), RoundedCornerShape(24.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = city.name, fontSize = 28.sp)
            Text(text = weather.condition.emoji(), fontSize = 72.sp)
            Text(text = formatTemperature(weather.temperature, useCelsius), fontSize = 48.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Wind: ${weather.windSpeed.roundToInt()} km/h")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = weather.condition.label())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherContentPreview() {
    val city = fakeCities.first()
    WeatherAndroidTIF24Theme {
        WeatherContent(
            uiState = WeatherUiState.Success(city, fakeCurrentWeather.getValue(city.name)),
        )
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
fun WeatherContentLoadingPreview() {
    WeatherAndroidTIF24Theme {
        WeatherContent(uiState = WeatherUiState.Loading)
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
fun WeatherContentErrorPreview() {
    WeatherAndroidTIF24Theme {
        WeatherContent(
            uiState = WeatherUiState.Error("Keine Verbindung zum Server"),
        )
    }
}
