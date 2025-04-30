package hu.ait.weatherreport.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hu.ait.weatherreport.R
import hu.ait.weatherreport.navigation.Routes
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun CityListScreen(navController: NavController, viewModel: CityListViewModel = viewModel()) {
    val cities by viewModel.cities.collectAsState()
    var newCity by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.welcome_message),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(cities) { city ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = city,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { navController.navigate(Routes.WEATHER_DETAIL + "/$city") }
                        )

                        IconButton(onClick = {
                            viewModel.removeCity(city)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = stringResource(id = R.string.delete_city_desc),
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            stringResource(id = R.string.add_city_title),
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                    },
                    text = {
                        TextField(
                            value = newCity,
                            onValueChange = { newCity = it },
                            singleLine = true
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (newCity.isNotBlank()) {
                                viewModel.addCity(newCity.trim())
                                newCity = ""
                                showDialog = false
                            }
                        }) {
                            Text(
                                stringResource(id = R.string.add_button),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(
                                stringResource(id = R.string.cancel_button),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                )
            }
        }
    }
}
