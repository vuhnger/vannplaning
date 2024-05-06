package no.uio.ifi.in2000.team_21.ui.home

// import androidx.compose.material.MaterialTheme
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import no.uio.ifi.in2000.team_21.Screen
import no.uio.ifi.in2000.team_21.model.activity.ActivityModel
import no.uio.ifi.in2000.team_21.ui.theme.HomeCard
import no.uio.ifi.in2000.team_21.ui.theme.HomeFont
import no.uio.ifi.in2000.team_21.ui.viewmodels.ActivitiesViewModel

@Composable
fun ActivityCard(
    activity : ActivityModel,
    viewModel: ActivitiesViewModel
) {
    val aspectRatio = 4f / 2f
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(2.dp)
            .background(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {

            Row(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    text = activity.activityName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                )
                Icon(
                    imageVector = Icons.Sharp.Add,
                    contentDescription = "Add Activity",
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(0.2f)
                        .clickable {
                            viewModel.addFavorite(activity)
                        }
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ){

                if (activity.imageId != null){
                    Image(
                        painter = painterResource(activity.imageId),
                        contentDescription = "Picture of ${activity.activityName}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(aspectRatio)
                    )
                }
            }
            Text(
                text = "Forholdene i dag oppsummert: ",
                fontSize = 8.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {

                // TODO: Hente ikoner for i dag

                WeatherIcon(element = "fair_day", size = 30)
                Spacer(modifier = Modifier.width(4.dp))
                WeatherIcon(element = "cloudy", size = 30)
                Spacer(modifier = Modifier.width(4.dp))
                WeatherIcon(element = "clearsky_day", size = 30)
                Spacer(modifier = Modifier.width(4.dp))

            }
            Text(
                text = "", // TODO: bruke beregning fra modell
                fontSize = 8.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Absolute.Right,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        //TODO: håndter start aktivitet. -> Logge aktiviteten?

                    },
                    modifier = Modifier
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Start",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// Kortene som ligger under Anbefaling seksjonen
@Composable
fun ActivityCardSmall(
    activity: ActivityModel,
    navController: NavController
){
    Card(
        modifier = Modifier
            .width(195.dp)
            .height(270.dp)
            .padding(start = 10.dp, top = 20.dp, end = 10.dp)
            .clickable {
                navController.navigate(
                    Screen.ActivityDetailScreen.withArgs(
                        activity.activityName
                    )
                )
            },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = HomeCard)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            
            Spacer(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = activity.activityName,
                modifier = Modifier.weight(1f),
                style = TextStyle(
                    color = HomeFont,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Icon(
                painter = painterResource(id = activity.icon),
                contentDescription = "Icon of ${activity.activityName}",
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ActivityIconSmall(
    activity: ActivityModel,
    activitiesViewModel: ActivitiesViewModel,
    navController: NavController
){
    Icon(
        painter = painterResource(id = activity.icon),
        contentDescription = "Icon of ${activity.activityName}",
        modifier = Modifier
            .padding(15.dp)
            .background(
                color = Color.White,
                shape = CircleShape
            ) // Endre bakgrunnsfarge til hvit og bruk sirkel som form
            .clip(CircleShape) // Klipper ikonet til en sirkelform
            //.border(2.dp, Color.Gray, CircleShape) // Legger til en tynn grå border rundt ikonet
            .padding(15.dp) // Justering for å beholde plassering og størrelse
            .scale(2f)
            .clickable {
                navController.navigate(
                    Screen.ActivityDetailScreen.withArgs(
                        activity.activityName
                    )
                )
            }
    )
}


@Composable
fun ActivityCardHorizontalWide(
    activity: ActivityModel,
    activitiesViewModel: ActivitiesViewModel
){

    var icon by remember {
        mutableStateOf(Icons.Outlined.FavoriteBorder)
    }

    Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(
            horizontal = 10.dp, vertical = 10.dp
        ),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = HomeCard)
    ) {

        Log.d(
            "HORIZONTAL_ACTIVITY_CARD",
            "initiated with activity: $activity, found in favorites: ${activity in activitiesViewModel.activityUIstate.favorites}"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HomeCard)
        ) {
            Icon(
                painter = painterResource(id = activity.icon),
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            )
            Button(
                onClick = {
                      if (activity in activitiesViewModel.activityUIstate.favorites){
                        activitiesViewModel.removeFavorite(activity = activity)
                          icon = Icons.Default.FavoriteBorder
                      }else{
                          activitiesViewModel.addFavorite(activity = activity)
                          icon = Icons.Default.Favorite
                      }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = HomeCard
                ),
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Knapp for å legge til i favoritter"
                )
            }
        }
    }
}

/*
* Lager Grid med aktiviteskort nedover, 2 og 2 per kolonne.

@Composable
fun ActivityCardGrid(
    forecastViewModel: OceanForecastViewModel,
    activitiesViewModel: ActivitiesViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(activitiesViewModel.activityUIstate.activities) { activity ->
            ActivityCard(activity = activity, viewModel = activitiesViewModel)
        }
    }
}

 */

/*


@Composable
fun ActivityCardGridHorizontal(
    activites: List<ActivityModel>,
    navController: NavController,
    activitiesViewModel: ActivitiesViewModel
){
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1)
    ) {
        items(activites){activity ->
            ActivityCardSmall(
                activity,
                navController
            )
        }
    }
}

 */

/*

*/
@Composable
fun ActivityCardGridHorizontalSmall(
    activitiesViewModel: ActivitiesViewModel,
    navController: NavController
) {
    if (activitiesViewModel.activityUIstate.favorites.isEmpty()){
        Card(

        ) {
            Text(text = "Du har ikke valgt noen favorittaktiviteter ennå, trykk på + for å legge til en!")
        }
    }else{
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = Modifier
                .height(84.dp)
        ) {
            items(activitiesViewModel.activityUIstate.favorites){activity ->
                ActivityIconSmall(
                    activity = activity,
                    activitiesViewModel = activitiesViewModel,
                    navController = navController
                )
            }
        }
    }
}
