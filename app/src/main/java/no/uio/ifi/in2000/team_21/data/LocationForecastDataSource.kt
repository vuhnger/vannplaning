package no.uio.ifi.in2000.team_21.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.team_21.model.locationforcast.LocationForecastResponse
import no.uio.ifi.in2000.team_21.model.locationforcast.LocationForecastTimeseries
import no.uio.ifi.in2000.team_21.model.oceanforecast.Timeseries

class LocationForecastDataSource {

    private var latitude: Double = 59.93
    private var longitude: Double = 10.72
    private var altitude: Int = 90


    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
        }
        install(Logging){
            level = LogLevel.BODY
        }
    }

    suspend fun fetchLocationForecastResponse(latitude: Double, longitude: Double): LocationForecastResponse? {

        val response: HttpResponse = client.get("https://in2000.api.met.no/weatherapi/locationforecast/2.0/complete?lat=${latitude}3&lon=$longitude")

        Log.d("LOCATION_DATA_SOURCE", "fetchLocationForcastResponse() status code: ${response.status.value}")

        return if (response.status.value in 200..299) {
            response.body()
        }
        else{
            null
        }
    }

    suspend fun fetchLocationForecastTimeseries(latitude: Double, longitude: Double): ArrayList<LocationForecastTimeseries>? {
        val response = fetchLocationForecastResponse(latitude, longitude)
        return response?.properties?.timeseries
    }

    suspend fun fetchLocationForecastByTime(time: String, latitude: Double, longitude: Double): LocationForecastTimeseries? {
        val timeseries = fetchLocationForecastTimeseries(latitude, longitude)
        return timeseries?.find { it.time == time }
    }
}