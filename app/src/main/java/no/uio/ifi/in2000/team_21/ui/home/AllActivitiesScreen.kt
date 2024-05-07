package no.uio.ifi.in2000.team_21.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.team_21.model.activity.ActivityModel
import no.uio.ifi.in2000.team_21.R
import no.uio.ifi.in2000.team_21.Screen
import no.uio.ifi.in2000.team_21.model.activity.ConditionStatus


@Composable
fun AllActivitiesScreen(
    navController: NavController,
    viewModel: ActivityConditionCheckerViewModel = ActivityConditionCheckerViewModel()
) {
    val activities by viewModel.activities.observeAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(activities) { activity ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFFBCCBFF)
            ) {
                ActivityListItem(activity = activity)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ActivityListItem(activity: ActivityModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween  // Distribute evenly
    ) {
        Column {
            Text(text = activity.activityName, color = Color(0xFF49454F)) // White text
            Text(getActivityConditionText(activity.conditionStatus, activity.activityName))
        }
        // Move Image composable to the end with Spacer for padding
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = activity.getFlagColorId()),
            contentDescription = null // Set contentDescription for accessibility
        )
        Spacer(modifier = Modifier.width(8.dp))  // Adjust spacing as needed
    }
}

fun getActivityConditionText(conditionStatus: ConditionStatus, activityName: String): String {
    return when (conditionStatus) {
        ConditionStatus.ALL_MET -> "Nå er det optimale forhold for $activityName"
        ConditionStatus.SOME_MET -> "Noen forhold er oppfylt for $activityName"
        ConditionStatus.NONE_MET -> "Ingen forhold er oppfylt for $activityName"
    }
}

@Preview
@Composable
fun PreviewAllActivitiesScreen() {
    val navController = rememberNavController()

    val mockViewModel = object : ActivityConditionCheckerViewModel() {
        init {
            checkActivityConditions("2024-05-06T20", 59.0, 10.0)
        }
    }

    AllActivitiesScreen(navController = navController, viewModel = mockViewModel)
}
