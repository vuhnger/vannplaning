package no.uio.ifi.in2000.team_21.ui.home



import no.uio.ifi.in2000.team_21.ui.viewmodels.LocationViewModel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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
import no.uio.ifi.in2000.team_21.ui.map.AlertsViewModel

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import no.uio.ifi.in2000.team_21.model.activity.ConditionStatus

import no.uio.ifi.in2000.team_21.ui.theme.Background
import no.uio.ifi.in2000.team_21.ui.theme.HomeCard
import no.uio.ifi.in2000.team_21.ui.theme.HomeFont
import no.uio.ifi.in2000.team_21.ui.theme.onContainerLight
import no.uio.ifi.in2000.team_21.ui.theme.weatherCardLight
import no.uio.ifi.in2000.team_21.ui.viewmodels.ActivitiesViewModel
import no.uio.ifi.in2000.team_21.ui.viewmodels.ActivityConditionCheckerViewModel
import no.uio.ifi.in2000.team_21.ui.viewmodels.ForecastViewModel
import no.uio.ifi.in2000.team_21.ui.viewmodels.OceanForecastViewModel

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.random.Random


@Composable
fun WeatherCard(
    cityName: String,
    temperature: String,
    alertColor: Color,
    isAlertActive: Boolean = false,
    icon: String,
    cloudCoverDescription: String,
    waveheight: String,
    windSpeed: String
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){

        Card(
            modifier = Modifier
                .padding(40.dp)
                .width(320.dp)
                .height(280.dp)
                .aspectRatio(1f),
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
                        color = onContainerLight,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = cityName,
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 20.sp,
                        //fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight(400),
                        color = onContainerLight,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
                Spacer(Modifier.height(16.dp))
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        WeatherIcon(
                            element = icon
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(
                            text = temperature,
                            style = TextStyle(
                                fontSize = 70.sp,
                                lineHeight = 16.sp,
                                //fontFamily = FontFamily(Font(R.font.roboto)),
                                //fontWeight = FontWeight(400),
                                color = onContainerLight,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp,
                            ),
                        )
                        if (isAlertActive) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Alert active",
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .size(40.dp)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = cloudCoverDescription,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        //fontFamily = FontFamily(Font(R.font.roboto)),
                        fontWeight = FontWeight(400),
                        color = onContainerLight,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
                Spacer(Modifier.height(4.dp))

                Row(

                ) {

                    Text(
                        text = "Vind: " + windSpeed,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            //fontFamily = FontFamily(Font(R.font.roboto)),
                            fontWeight = FontWeight(400),
                            color = onContainerLight,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.1.sp,
                        ),
                        modifier = Modifier
                            .weight(1f)
                    )

                    if ( !waveheight.contains("null")){
                        Text(
                            text = "Bølger: " + waveheight,
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                //fontFamily = FontFamily(Font(R.font.roboto)),
                                fontWeight = FontWeight(400),
                                color = onContainerLight,
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
}


@Composable
fun ActivityFavorites(
    viewModel: ActivitiesViewModel,
    navController: NavController,
    activityConditionCheckerViewModel: ActivityConditionCheckerViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Dine favoritter",
                style = TextStyle(
                    fontSize = 20.sp,
                    //lineHeight = 20.sp,
                    //fontFamily = FontFamily(Font(R.font.roboto)),
                    //fontWeight = FontWeight(400),
                    color = onContainerLight,
                    //textAlign = TextAlign.Center,
                    letterSpacing = 0.1.sp,
                ),
                modifier = Modifier
                    .padding(8.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Screen.AddActivityScreen.route)
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Background,
                    contentColor = onContainerLight
                ),
                modifier = Modifier
                    .offset(x = 200.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Button add to favorites",
                    modifier = Modifier
                        .padding(1.dp)
                        .scale(1.5f)
                )
            }

        }

        Spacer(Modifier.height(8.dp))

        // Denne komponenten tegner bare ikonene under "Favoritt"-seksjonen
        ActivityCardGridHorizontalSmall(
            navController = navController,
            activitiesViewModel = viewModel
        )

    }
}

@Composable
fun RecommendationSection(
    viewModel: ActivitiesViewModel,
    activityConditionCheckerViewModel: ActivityConditionCheckerViewModel,
    locationViewModel: LocationViewModel,
    navController: NavController
) {

    val weatherFacts = listOf(
        "De beste seilforholdene oppstår ofte når det er en jevn bris på 10-20 knop. Denne vindhastigheten ikke bare driver båter jevnt, men gir også behagelig motstand for mer spennende seilopplevelser.",
        "Visste du at vann beholder varme lenger enn luft? Selv på en kjøligere dag kan vannet i innsjøer og hav være behagelig varmt, noe som gjør det mulig for en komfortabel svømmetur.",
        "Vann leder elektrisitet, noe som gjør det farlig under tordenvær. Statistisk sett skjer flertallet av lynskader og dødsfall på vannet i små båter uten kabin.",
        "Tåke er vanligere nær vannkropper fordi vannpartikler som er suspendert i luften kjøler ned den omkringliggende temperaturen, noe som kondenserer luftfuktigheten. Roere lærer å lytte etter lydene av andre båters horn og å holde seg nær land for å navigere trygt i tåke.",
        "Tidevannsstrømmer kan påvirke kajakkopplevelsen din betraktelig. Å padle med tidevannet kan hjelpe deg å bevege deg raskere og med mindre innsats, mens det å padle mot tidevannet kan være en skikkelig treningsøkt!",
        "UV-stråler reflekterer av vannoverflaten, noe som øker eksponeringen din. Dette betyr at entusiaster for vannsport trenger å påføre solkrem mer rikelig og oftere enn når de er på land.",
        "Fisk er mer sannsynlig å bite når det er et plutselig fall i trykket, noe som ofte kommer før dårlig vær. Dette er et nyttig tips for kajakfiskere for å planlegge sine turer.",
        "Høy fuktighet kan gjøre at det føles mye varmere enn det faktisk er fordi svette ikke fordamper raskt for å kjøle ned kroppen. Dette er spesielt viktig for vannsport, da det kan påvirke hydrering og energinivåer.",
        "Økt vindstyrke kan skape høyere bølger, noe som er ideelt for mer avanserte kajakkpadlere som søker utfordringer.",
        "I kaldere klima kan sjøvann noen ganger holde seg over frysepunktet selv når lufttemperaturen faller under null, takket være saltinnholdet som senker frysepunktet.",
        "Sikten under vann kan forbedres betydelig når vindstyrken avtar og vannet blir roligere, noe som er perfekt for snorkling og dykking.",
        "Regn kan påvirke vannets salinitet (saltinnhold), spesielt nær kysten, noe som kan påvirke marine økosystemer og den lokale fiskeaktiviteten.",
        "Soloppgang og solnedgang er ofte de beste tidene for padling fordi vindforholdene er roligere og temperaturen er mer behagelig.",
        "Langvarig eksponering for kaldt vann kan redusere kroppstemperaturen og øke risikoen for hypotermi, selv for svømmere som er vant til kaldt vann.",
        "Månefasene kan dramatisk påvirke tidevannsstyrken, noe som er viktig å vurdere når man planlegger aktiviteter som krever at man går inn og ut av vannet.",
        "Rask endring i lufttrykk kan føre til raskt skiftende værforhold på vannet, noe som krever at vannsportentusiaster alltid må være forberedt på endringer.",
        "Duggpunktet kan fortelle mye om hvor raskt været vil endre seg, spesielt viktig for de som planlegger langvarige aktiviteter på vannet.",
        "Algerblomstring, som ofte oppstår under spesielle temperatur- og næringsforhold, kan gjøre vannet farlig for svømming og andre vannaktiviteter.",
        "Under varmebølger kan sjøvannet bli så varmt at det stimulerer overdreven vekst av bakterier, noe som kan gjøre vannet usikkert for bading.",
        "Stormer kan skape ideelle forhold for surfeentusiaster, da de ofte fører med seg større og mer kraftfulle bølger.",
        "Isdannelse på innsjøer starter ved kantene og vokser innover, og tykkelsen kan variere sterkt, noe som er viktig for isfiskere og skøyteentusiaster å merke seg.",
        "Å seile ved fullmåne kan gi en spektakulær nattseilasopplevelse, da månelyset reflekterer over vannflaten og forbedrer synligheten.",
        "Vannsprut fra kraftige bølger kan redusere synligheten betydelig, noe som gjør navigasjon på vannet mer utfordrende under stormfulle forhold.",
        "Lyden av bølger er høyere når lufttemperaturen er lavere, da kaldere luft er tyngre og bærer lyd bedre.",
        "Termiske vinder, som oppstår når varm luft stiger og kaldere luft strømmer inn for å erstatte den, kan gi perfekte forhold for kite- og vindsurfing.",
        "Morgentåke langs elver og innsjøer kan ofte indikere en klar dag fremover, da tåken dannes når nattens kjølige temperatur møter varmere vann.",
        "Vannet kan virke mørkere før stormer på grunn av skyggen fra tykke skyer, noe som kan påvirke synligheten for dykkere og snorklere.",
        "Høyere luftfuktighet kan føre til at kroppen føler seg klam under vannsport, selv i relativt kjølige forhold."
    )

    fun getRandomWeatherFact(): String {
        return weatherFacts[Random.nextInt(weatherFacts.size)]
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Våre anbefalinger",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(400),
                color = onContainerLight,
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            )
        )
        Spacer(Modifier.height(8.dp))

        val activityList by activityConditionCheckerViewModel.activities.observeAsState(initial = emptyList())
        val filteredActivities = activityList.filter {
            it.conditionStatus == ConditionStatus.ALL_MET
        }

        if(filteredActivities.isEmpty()){
            Card(

            ) {

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ingen aktiviteter kan anbefales akkurat nå. Dette kan skyldes upassende vær eller manglende nettverkstilkobling."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mens du venter, her er en fakta om vær ved sjøen"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = getRandomWeatherFact())
            }
        }else{
            LazyRow {
                this.items(filteredActivities){ recommendation ->
                    ActivityCardSmall(
                        activity = recommendation,
                        navController = navController
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController
) {
    TopAppBar(
        title = {  },
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(Modifier.weight(1f))

                Row {
                    IconButton(
                        onClick = {
                                  // TODO: Tilbake navigering
                                  },
                        modifier = Modifier
                            .sizeIn(minWidth = 96.dp, minHeight = 48.dp)
                    ) {
                        Text("Hjem", style = TextStyle(
                            fontSize = 20.sp
                        )
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate(Screen.MapScreen.route) },
                        modifier = Modifier
                            .sizeIn(minWidth = 96.dp, minHeight = 48.dp)
                    ) {
                        Text("Kart", style = TextStyle(
                            fontSize = 20.sp
                        ))
                    }
                }

                Box(Modifier.weight(1f)) {
                    IconButton(onClick = { navController.navigate(Screen.SettingScreen.route) }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Account icon",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .padding(top = 16.dp)
    )

}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    activitiesViewModel: ActivitiesViewModel,
    forecastViewModel: ForecastViewModel,
    alertsViewModel: AlertsViewModel,
    locationViewModel: LocationViewModel,
    activityConditionCheckerViewModel: ActivityConditionCheckerViewModel,
    oceanForecastViewModel: OceanForecastViewModel
) {

    val norwayZone = ZoneId.of("Europe/Oslo")

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH").withZone(norwayZone)

    val time = ZonedDateTime.now(norwayZone).truncatedTo(ChronoUnit.HOURS).format(formatter)

    val userLocation by locationViewModel.userLocation.collectAsState()
    val filteredFeatures by alertsViewModel.filteredFeatures.observeAsState()
    val oceanData by oceanForecastViewModel.oceanDataState.observeAsState()
    val currentCityName by locationViewModel.currentCityName.collectAsState()
    val currentForcastResponse by forecastViewModel.forecast.collectAsState()

    var showNoNetworkDialog by remember {
        mutableStateOf(false)
    }

    if (showNoNetworkDialog){
        AlertDialog(
            onDismissRequest = {
                showNoNetworkDialog = false
                               },
            title = { Text(text = "Ingen nettverksforbindelse")},
            text = { Text(text = "Vi kan ikke hente værdata, sjekk din nettverkstilkobling og prøv igjen. ")},
            buttons = {
                Button(onClick = { showNoNetworkDialog = false }) {
                    Text(text = "Lukk")
                }
            }
        )
    }

    Log.d(
        "HOME_SCREEN",
        "oceanData: ${oceanData?.properties?.timeseries}"
    )

    val currentForecast = currentForcastResponse?.properties?.timeseries?.find {
        it.time?.contains(time) ?: false
    }

    val isAlertActive = remember(filteredFeatures) {
        filteredFeatures?.isNotEmpty() == true
    }

    LaunchedEffect(userLocation) {
        if (userLocation != null) {
            alertsViewModel.fetchAndFilterAlerts(
                AlertsInfo(),
                userLocation!!,
                radius = 20.0
            )
            Log.d("HOME_SCREEN", "User location: ${userLocation!!.latitude()}, ${userLocation!!.longitude()}")

            forecastViewModel.continuousForecastUpdate( // let him cook!
                latitude = userLocation!!.latitude(),
                longitude = userLocation!!.longitude()
            )

            activityConditionCheckerViewModel.checkActivityConditions(
                time = time,
                latitude = userLocation!!.latitude() ,
                longitude = userLocation!!.longitude()
            )

            oceanForecastViewModel.fetchOceanForecastByTime(
                latitude = userLocation!!.latitude(),
                longitude = userLocation!!.longitude()
            )

        }
    }

    val alertColor = when (filteredFeatures?.maxByOrNull { it.properties.severity?.toIntOrNull() ?: 0 }?.properties?.riskMatrixColor) {
        "Yellow" -> Color(0xFFF9F1DC) // Yellow
        "Red" -> Color(0xFFF9DEDC) // Red
        "Green" -> Color(0xFFECF9DC) // Green
        else -> weatherCardLight // Default case
    }

    Column(
        modifier = Modifier
            .width(360.dp)
            .height(50.dp)
            .background(color = Background)
    ) {

        TopBarComponent(
            navController = navController
        )

        if(currentForcastResponse != null){
            WeatherCard(
                cityName = currentCityName ?: "---",
                temperature = when (currentForcastResponse?.properties?.meta?.units?.air_temperature) {
                    "celsius" -> "${currentForecast?.data?.instant?.details?.air_temperature?.toInt().toString()}°"
                    else -> currentForecast?.data?.instant?.details?.air_temperature?.toInt().toString()
                },
                alertColor = alertColor,
                isAlertActive = isAlertActive,
                cloudCoverDescription = forecastViewModel.describeCloudCover(
                    currentForecast?.data?.instant?.details?.cloud_area_fraction ?: 1.1
                ),
                icon = currentForecast?.data?.next_1_hours?.summary?.symbol_code ?: "",
                waveheight = "${oceanData?.properties?.timeseries?.find { it.time?.contains(time) ?: false}?.data?.instant?.details?.sea_surface_wave_height} ${oceanData?.properties?.meta?.units?.sea_surface_wave_height}",
                windSpeed = "${currentForecast?.data?.instant?.details?.wind_speed} ${currentForcastResponse?.properties?.meta?.units?.wind_speed}"
            )
        }else{
            showNoNetworkDialog = true
        }

        ActivityFavorites(
            viewModel = activitiesViewModel,
            navController = navController,
            activityConditionCheckerViewModel = activityConditionCheckerViewModel
        )

        RecommendationSection(
            viewModel = activitiesViewModel,
            activityConditionCheckerViewModel = activityConditionCheckerViewModel,
            locationViewModel = locationViewModel,
            navController = navController
        )
    }
}

