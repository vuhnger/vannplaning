package no.uio.ifi.in2000.team_21.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.team_21.model.locationforecast.Response

open class LocationForecastDataSource {

    private val lat: Double = 59.94
    private val lon: Double = 10.71
    private val altitude = 100

    private val url = "https://api.met.no/weatherapi/locationforecast/2.0/complete?" +
            "lat=$lat" +
            "&lon=$lon" +
            "&altitude=$altitude"

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging){
            level = LogLevel.BODY
        }
    }

    suspend fun fetchForecast(): Response? {

        val response: HttpResponse = client.get(url)


        /*
        *
        Log.d(
            "LFC_DATA_SOURCE",
            "fetchForecast() returned code ${response.status.value}"
        )
        *
        * */

        return if (response.status.value in 200..299) {
            response.body()
        }
        else{
             null
        }
    }
}
