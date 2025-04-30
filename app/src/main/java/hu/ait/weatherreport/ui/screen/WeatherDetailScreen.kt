package hu.ait.weatherreport.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.ait.weatherreport.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherDetailScreen(
    city: String,
    apiKey: String,
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weather by viewModel.weather.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(city) {
        viewModel.loadWeather(city, apiKey)
    }

    if (error != null) {
        AlertDialog(
            onDismissRequest = {
                viewModel.clearError()
                navController.popBackStack()
            },
            title = { Text(text = stringResource(id = R.string.error_title)) },
            text = { Text(text = stringResource(id = R.string.error_message)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearError()
                    navController.popBackStack()
                }) {
                    Text("OK")
                }
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.back_text),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { navController.popBackStack() }
                    .padding(bottom = 16.dp)
            )

            weather?.let { data ->
                AsyncImage(
                    model = "https://openweathermap.org/img/w/${data.weather?.firstOrNull()?.icon}.png",
                    contentDescription = stringResource(id = R.string.weather_icon_desc),
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 12.dp)
                )

                Text(
                    text = data.name ?: "",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 24.dp)
                )


                Column(modifier = Modifier.align(Alignment.Start)) {
                    TextLabelValue(label = "Coordinates", value = "${data.coord?.lon}, ${data.coord?.lat}")
                    TextLabelValue(label = "Weather", value = data.weather?.firstOrNull()?.main)
                    TextLabelValue(label = "Description", value = data.weather?.firstOrNull()?.description)
                    TextLabelValue(label = "Temperature", value = "${data.main?.temp}°C")
                    TextLabelValue(label = "Pressure", value = "${data.main?.pressure} hPa")
                    TextLabelValue(label = "Humidity", value = "${data.main?.humidity}%")
                    TextLabelValue(label = "Visibility", value = "${data.visibility} m")
                    TextLabelValue(label = "Wind Speed", value = "${data.wind?.speed} m/s")
                    TextLabelValue(label = "Wind Degree", value = "${data.wind?.deg}°")
                    TextLabelValue(label = "Wind Gust", value = "${data.wind?.gust} m/s")
                    TextLabelValue(label = "Cloudiness", value = "${data.clouds?.all}%")
                    TextLabelValue(label = "Country", value = data.sys?.country)
                }
            } ?: Text(
                text = stringResource(id = R.string.loading),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun TextLabelValue(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

fun Long.toFormattedTime(): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}
