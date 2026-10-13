package com.example.weatherandroidtif24.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherandroidtif24.data.fakeCities
import com.example.weatherandroidtif24.model.City
import com.example.weatherandroidtif24.ui.theme.WeatherAndroidTIF24Theme

@Composable
fun CityListScreen(
    onCityClick: (City) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CityListViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CityListContent(
        uiState = uiState,
        onSearch = viewModel::search,
        onCityClick = onCityClick,
        onSearchResultClick = { city ->
            viewModel.addCity(city)
            onCityClick(city)
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListContent(
    uiState: CityListUiState,
    onSearch: (String) -> Unit,
    onCityClick: (City) -> Unit,
    onSearchResultClick: (City) -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = "Städte") }) },
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            item {
                CitySearchField(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { onSearch(query) },
                )
                if (uiState.isSearching) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                uiState.errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
            if (uiState.searchResults.isNotEmpty()) {
                item { SectionHeader(text = "Suchergebnisse") }
                // Mehrere Städte können gleich heißen, deshalb Koordinaten als Key
                items(uiState.searchResults, key = { "result-${it.latitude},${it.longitude}" }) { city ->
                    CityRow(city = city, onClick = { onSearchResultClick(city) })
                }
            }
            item { SectionHeader(text = "Meine Städte") }
            items(uiState.cities, key = { "${it.latitude},${it.longitude}" }) { city ->
                CityRow(city = city, onClick = { onCityClick(city) })
            }
        }
    }
}

@Composable
fun CitySearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text(text = "Stadt suchen") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            modifier = Modifier.weight(1f),
        )
        Button(onClick = onSearch) {
            Text(text = "Suchen")
        }
    }
}

@Composable
fun SectionHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
fun CityRow(city: City, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(text = city.name) },
        supportingContent = {
            Text(text = "%.2f° N, %.2f° O".format(city.latitude, city.longitude))
        },
        modifier = modifier.clickable(onClick = onClick),
    )
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
fun CityListContentPreview() {
    WeatherAndroidTIF24Theme {
        CityListContent(
            uiState = CityListUiState(
                cities = fakeCities,
                searchResults = listOf(City("Hamburg", 53.55, 9.99)),
            ),
            onSearch = {},
            onCityClick = {},
            onSearchResultClick = {},
        )
    }
}
