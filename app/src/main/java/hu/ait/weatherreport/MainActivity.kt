package hu.ait.weatherreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.weatherreport.navigation.Routes
import hu.ait.weatherreport.ui.screen.CityListScreen
import hu.ait.weatherreport.ui.screen.WeatherDetailScreen
import hu.ait.weatherreport.ui.theme.WeatherReportTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val apiKey = "33a5b8db5ca2a90b8d47738bcfdec33f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherReportTheme {
                WeatherAppNav(apiKey)
            }
        }
    }
}

@Composable
fun WeatherAppNav(apiKey: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.CITY_LIST) {
        composable(Routes.CITY_LIST) {
            CityListScreen(navController)
        }
        composable(
            "${Routes.WEATHER_DETAIL}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            WeatherDetailScreen(
                city = city,
                apiKey = apiKey,
                navController = navController
            )
        }
    }
}



