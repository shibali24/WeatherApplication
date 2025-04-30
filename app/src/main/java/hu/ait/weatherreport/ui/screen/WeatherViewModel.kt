package hu.ait.weatherreport.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.weatherreport.data.Weather
import hu.ait.weatherreport.network.WeatherAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherAPI: WeatherAPI
) : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                _weather.value = weatherAPI.getWeather(city, apiKey)
                _error.value = null
            } catch (e: Exception) {
                _weather.value = null
                _error.value = "City not found or network error"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
