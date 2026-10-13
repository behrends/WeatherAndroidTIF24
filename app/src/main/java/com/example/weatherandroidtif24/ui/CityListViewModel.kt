package com.example.weatherandroidtif24.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherandroidtif24.data.ApiCityRepository
import com.example.weatherandroidtif24.data.CityRepository
import com.example.weatherandroidtif24.data.fakeCities
import com.example.weatherandroidtif24.model.City
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CityListViewModel(
    private val repository: CityRepository = ApiCityRepository(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(CityListUiState(cities = fakeCities))
    val uiState: StateFlow<CityListUiState> = _uiState.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) return
        _uiState.update { it.copy(isSearching = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                val results = repository.searchCities(query.trim())
                _uiState.update {
                    it.copy(
                        searchResults = results,
                        isSearching = false,
                        errorMessage = if (results.isEmpty()) "Keine Stadt gefunden" else null,
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isSearching = false, errorMessage = e.message ?: "Suche fehlgeschlagen")
                }
            }
        }
    }

    fun addCity(city: City) {
        _uiState.update { state ->
            val alreadyAdded = state.cities.any {
                it.latitude == city.latitude && it.longitude == city.longitude
            }
            state.copy(
                cities = if (alreadyAdded) state.cities else state.cities + city,
                searchResults = emptyList(),
                errorMessage = null,
            )
        }
    }
}
