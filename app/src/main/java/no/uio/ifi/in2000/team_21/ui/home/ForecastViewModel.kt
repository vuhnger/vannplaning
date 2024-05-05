package no.uio.ifi.in2000.team_21.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team_21.data.LocationForecastDataRepository
import no.uio.ifi.in2000.team_21.model.locationforcast.Details
import no.uio.ifi.in2000.team_21.model.locationforcast.LocationForecastResponse
import no.uio.ifi.in2000.team_21.model.locationforcast.LocationForecastTimeseries
import no.uio.ifi.in2000.team_21.model.locationforcast.WeatherData
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
class ForecastViewModel(

) : ViewModel() {

    private val repository: LocationForecastDataRepository = LocationForecastDataRepository()
    private val _selectedLocationWeatherData = mutableStateOf<List<LocationForecastTimeseries>?>(null)
    val selectedLocationWeatherData: State<List<LocationForecastTimeseries>?> = _selectedLocationWeatherData
    private val _weatherDataState = MutableStateFlow<WeatherDataState>(WeatherDataState.Loading)
    val weatherDataState: StateFlow<WeatherDataState> = _weatherDataState

    private var _forecast = MutableStateFlow<LocationForecastResponse?>(null)
    val forecast: StateFlow<LocationForecastResponse?> = _forecast.asStateFlow()

    var instant_air_temperature by mutableStateOf(
        0.0
    )

    var next_1_hour_weather_icon by mutableStateOf(
        ""
    )


    // Private mutable LiveData for icons
    var icons by mutableStateOf(
        listOf<String>()
    )

    fun fetchTodaysForecast(
        latitude: Double,
        longitude: Double
    ){
        viewModelScope.launch(Dispatchers.IO) {


            val norwayZone = ZoneId.of("Europe/Oslo")


            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH").withZone(norwayZone)


            val time = ZonedDateTime.now(norwayZone).truncatedTo(ChronoUnit.HOURS).format(formatter)

            Log.d(
                "FORECAST_VIEW_MODEL",
                "attempted to fetch forecast at time: $time"
            )

            val response: LocationForecastResponse? = repository.fetchForecast(
                latitude = latitude,
                longitude = longitude
            )

            _forecast.value = response
        }
    }

    fun continuousForecastUpdate(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                while (true) {
                    val forecast = repository.fetchForecast(latitude, longitude)
                    emit(forecast)
                    delay(30_000) // Delay for 30 seconds
                }
            }.collect { forecast ->
                _forecast.value = forecast
            }
        }
    }

    fun fetchNextHourWeatherIcon(
        time: String,
        latitude: Double,
        longitude: Double
    ){
        viewModelScope.launch {
            val response: String = repository.repositoryfetchNextHourWeatherIcon(
                time = time,
                latitude = latitude,
                longitude = longitude
            )
            next_1_hour_weather_icon = response
        }
    }

    fun fetchCurrentAirTemperature(
        latitude: Double,
        longitude: Double
    ){
        viewModelScope.launch {
            val response: Double = repository.fetchCurrentAirTemperature(
                latitude = latitude,
                longitude = longitude
            )
            instant_air_temperature = response
        }
    }

    fun fetchIcons(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            val response = repository.fetchAllNext1HourImageIcons(
                latitude = latitude,
                longitude = longitude
            )
            icons = response
        }
    }

    fun fetchForecast(
        latitude: Double,
        longitude: Double
    ){
        viewModelScope.launch {
            val response: LocationForecastResponse? = repository.fetchForecast(
                latitude = latitude,
                longitude = longitude
            )
            _forecast.value = response
        }
    }

    fun fetchWeatherForLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            val timeseriesData = repository.fetchWeatherDataForLocation(lat, lon)
            _selectedLocationWeatherData.value = timeseriesData
        }
    }

    sealed class WeatherDataState {
        object Loading : WeatherDataState()
        data class Success(val weatherData: WeatherData?) : WeatherDataState()
        data class Error(val message: String) : WeatherDataState()
    }

    fun fetchWeatherDataByTime(time: String, latitude: Double, longitude: Double) {
        _weatherDataState.value = WeatherDataState.Loading
        viewModelScope.launch {
            val timeseries = repository.fetchLocationForecastTimeseriesByTime(time ,latitude, longitude) // Assuming this fetches by location
            val weatherData = timeseries?.data?.instant?.details?.let { transformToWeatherData(it, timeseries) }
            _weatherDataState.value = if (weatherData != null) {
                WeatherDataState.Success(weatherData)
            } else {
                WeatherDataState.Error("No data found for time: $time")
            }
        }
    }

    private fun transformToWeatherData(details: Details, timeseries: LocationForecastTimeseries): WeatherData? {

        return WeatherData(
            time = timeseries.time,
            airTemperature = details.air_temperature,
            windFromDirection = details.wind_from_direction,
            windSpeed = details.wind_speed,
            humidity = details.relative_humidity,
            chanceOfRain = details.probability_of_precipitation,
            // Add other fields based on your needs and response structure (e.g., air pressure, cloud cover)
        )
    }

}
