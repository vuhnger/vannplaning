package no.uio.ifi.in2000.team_21.ui.home
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import no.uio.ifi.in2000.team_21.Screen

// Top bar implementation: to work as one component to be used through all the screens.
// Dataclass to define each tab in the navbar
data class TopNavItem(val title: String, val icon: @Composable (() -> Unit)? = null)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    items : List<TopNavItem>,
    currentSelection: Int,
    onItemSelected: (Int) -> Unit

) {
    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Hjem")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button $presses times.
                """.trimIndent(),
            )
        }
    }
}

    
@Composable
fun RowScope.TopBarItem(item: TopNavItem, isSelected: Boolean, onItemSelect: () -> Unit) {
    Tab(
        selected = isSelected,
        onClick = onItemSelect,
        text = { Text(item.title) },
        icon = item.icon
    )
}

@Composable
fun WeatherCard(temperature: String, highLowTemp: String, weatherCondition: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .background(
                    color = Color(0xFFF7F7F7),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(40.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            )
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    WeatherIcon(
                        element = "fair_day",
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
                        )
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = weatherCondition,
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
                Text(
                    text = highLowTemp,
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
            }
        }
    }

}

@Composable
fun ActivityFavorites(activities: MutableList<no.uio.ifi.in2000.team_21.model.Activity>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Favoritter",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(400),
                color = Color(0xFF00145D),
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            )
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            activities.forEach {activity ->
               ActivityCardSmall()
            }
        }
    }
}
@Composable
fun ActivityIcon(activity: no.uio.ifi.in2000.team_21.model.Activity) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        /*Icon(/* ... */) */
        /*Text(activity.name)*/
        Text("Aktivitet")

    }
}
@Composable
fun RecommendationSection(
    recommendations: List<no.uio.ifi.in2000.team_21.model.Activity>,
    viewModel: ActivitiesViewModel
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Anbefaling",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                //fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight(400),
                color = Color(0xFF00145D),
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            )
        )
        Spacer(Modifier.height(8.dp))
        LazyRow {
            this.items(recommendations){ recommendation ->
                // TODO: ActivityCardGridHorizontal her
            }
            }
        }
    }

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ActivitiesViewModel = ActivitiesViewModel()
) {
    //TopBar(items = TopNavItem<items>, currentSelection = 1 ) {}

    Column(
        modifier = Modifier
            .width(360.dp)
            .height(50.dp)
            .background(color = Color(0xFFF7F8FF))
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF00145D),
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .weight(0.5f)

            ) {
                Text(
                    text = "Hjem",
                    modifier = Modifier
                        .weight(0.5f)
                        .offset(x = 110.dp) // Flytter tekst-elementet lengre til høyre
                )
            }

            Button(
                onClick = { navController.navigate(Screen.MapScreen.route)},
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF00145D),
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .weight(0.5f)
            ) {
                Text(
                    text = "Kart",
                    modifier = Modifier
                        .weight(0.5f)
                )
            }

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account icon",
                modifier = Modifier
                    .clickable { navController.navigate(Screen.SettingScreen.route) }
            )

        }

        WeatherCard(
            temperature = "14°",
            highLowTemp = "H:16° L:3°",
            weatherCondition = "Skyfritt"
        )
        ActivityFavorites(
            activities = viewModel.activityUIstate.favorites
        )
        RecommendationSection(
            recommendations = viewModel.activityUIstate.activities,
            viewModel = viewModel
        )
    }
}
