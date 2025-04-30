package hu.ait.weatherreport.ui.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CityListViewModel : ViewModel() {
    private val _cities = MutableStateFlow(mutableListOf("Budapest", "London", "Boston"))
    val cities: StateFlow<List<String>> = _cities

    fun addCity(city: String) {
        _cities.value = _cities.value.toMutableList().apply { add(city) }
    }

    fun removeCity(city: String) {
        _cities.value = _cities.value.toMutableList().apply { remove(city) }
    }
}