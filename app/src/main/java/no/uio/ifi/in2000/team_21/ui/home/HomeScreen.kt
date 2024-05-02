package no.uio.ifi.in2000.team_21.ui.home


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import no.uio.ifi.in2000.team_21.Screen
import no.uio.ifi.in2000.team_21.model.AlertsInfo
import no.uio.ifi.in2000.team_21.ui.LocationViewModel
import no.uio.ifi.in2000.team_21.ui.map.AlertsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import no.uio.ifi.in2000.team_21.ui.map.MapboxMapView

@Composable
fun WeatherCard(
    temperature: String,
    alertColor: Color = Color(0xFFF7F7F7),
    isAlertActive: Boolean = false,
    highTemp: String,
    lowTemp: String,
    icon: String
) {

    Log.d(
        "WEATHER_CARD",
        "drawn card with temp: $temperature, low: $lowTemp, high: $highTemp, icon: $icon"
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = alertColor)
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Min posisjon",
                    style = TextStyle(
                        fontSize = 25.sp,
                        lineHeight = 20.sp,
                        //fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF00145D),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Oslo",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 20.sp,
                        //fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF00145D),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
                Spacer(Modifier.height(16.dp))
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        WeatherIcon(
                            element = icon,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = temperature,
                            style = TextStyle(
                                fontSize = 70.sp,
                                lineHeight = 16.sp,
                                //fontFamily = FontFamily(Font(R.font.roboto)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF00145D),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp,
                            ),
                        )
                        if (isAlertActive) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Alert active",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(48.dp)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = icon,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        //fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF00145D),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
                Spacer(Modifier.height(4.dp))

                Row(

                ) {
                    Text(
                        text = "H: " + highTemp,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            //fontFamily = FontFamily(Font(R.font.roboto)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF00145D),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.1.sp,
                        ),
                        modifier = Modifier
                            .weight(1f)
                    )

                    Text(
                        text = "L: " + lowTemp ,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            //fontFamily = FontFamily(Font(R.font.roboto)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF00145D),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.1.sp,
                        ),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                }

        }
    }

}

@Composable
fun ActivityFavorites(
    viewModel: ActivitiesViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = "Favoritter",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    //fontFamily = FontFamily(Font(R.font.roboto)),
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.1.sp,
                ),
                modifier = Modifier
                    .weight(1f)
            )

            Button(
                onClick = { navController.navigate(Screen.AddActivitiyScreen.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.material.MaterialTheme.colors.background,
                    contentColor = androidx.compose.material.MaterialTheme.colors.primary
                ),
                modifier = Modifier
                    .weight(1f)
                    .width(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Button add to favorites",
                    modifier = Modifier
                        .padding(1.dp)
                )
            }

        }

        Spacer(Modifier.height(8.dp))

        ActivityCardGridHorizontalSmall(
            activitiesViewModel = viewModel
        )

    }
}

@Composable
fun RecommendationSection(
    viewModel: ActivitiesViewModel,
    navController: NavController
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Anbefaling",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            )
        )
        Spacer(Modifier.height(8.dp))
        LazyRow {
            this.items(viewModel.activityUIstate.activities){ recommendation ->
                ActivityCardSmall(
                    activity = recommendation,
                    navController = navController,
                    activitiesViewModel = viewModel
                )
            }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = { /* No title for now */ },
        actions = {
            IconButton(onClick = { /* Navigate to home screen */ }) {
                Text("Hjem")
            }
            IconButton(onClick = { navController.navigate(Screen.MapScreen.route) }) {
                Text("Kart")
            }
            IconButton(onClick = { navController.navigate(Screen.SettingScreen.route) }) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Account icon")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    activitiesViewModel: ActivitiesViewModel,
    forecastViewModel: ForecastViewModel,
    alertsViewModel: AlertsViewModel,
    locationViewModel: LocationViewModel
) {

    val userLocation by locationViewModel.userLocation.collectAsState()
    val filteredFeatures by alertsViewModel.filteredFeatures.observeAsState()

    val isAlertActive = remember(filteredFeatures) {
        filteredFeatures?.isNotEmpty() == true
    }

    LaunchedEffect(userLocation) {
        if (userLocation != null) {
            alertsViewModel.fetchAndFilterAlerts(AlertsInfo(), userLocation!!, 500.0)
            Log.d("HOME_SCREEN", "User location: ${userLocation!!.latitude()}, ${userLocation!!.longitude()}")
            forecastViewModel.fetchTodaysForecast( // let him cook!
                latitude = userLocation?.latitude() ?: 0.0,
                longitude = userLocation?.longitude() ?: 0.0
            )
        }
    }

    val alertColor = when (filteredFeatures?.maxByOrNull { it.properties.severity?.toIntOrNull() ?: 0 }?.properties?.riskMatrixColor) {
        "Yellow" -> Color(0xFFF9F1DC) // Yellow
        "Red" -> Color(0xFFF9DEDC) // Red
        "Green" -> Color(0xFFECF9DC) // Green
        else -> Color(0xFFF7F7F7) // Default case
    }

    Column(
        modifier = Modifier
            .width(360.dp)
            .height(50.dp)
            .background(color = Color(0xFFF7F8FF))
    ) {

        TopBar(
            navController = navController
        )

        WeatherCard(
            alertColor = alertColor,
            isAlertActive = isAlertActive,
            temperature = forecastViewModel.today_forecast?.data?.instant?.details?.air_temperature?.toString() ?: "N/A",
            highTemp = forecastViewModel.today_forecast?.data?.instant?.details?.air_temperature_max?.toString() ?: "N/A",
            lowTemp = forecastViewModel.today_forecast?.data?.instant?.details?.air_temperature_min?.toString() ?: "N/A",
            icon = forecastViewModel.today_forecast?.data?.next_1_hours?.summary?.symbol_code?.toString() ?: "N/A"
        )

        ActivityFavorites(
            viewModel = activitiesViewModel,
            navController = navController
        )

        RecommendationSection(
            viewModel = activitiesViewModel,
            navController = navController
        )
    }
}
