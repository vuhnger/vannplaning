package no.uio.ifi.in2000.team_21.ui.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team_21.data.database.ActivitiesDao
import no.uio.ifi.in2000.team_21.data.database.ActivityEntity
import no.uio.ifi.in2000.team_21.data.database.AppDatabase
import no.uio.ifi.in2000.team_21.data.database.DatabaseBuilder
import no.uio.ifi.in2000.team_21.model.activity.ActivityLog
import no.uio.ifi.in2000.team_21.model.activity.ActivityModel
import no.uio.ifi.in2000.team_21.model.activity.ActivityModels


data class ActivitiesUIState(
    val activities: List<ActivityModel>,
    val favorites: MutableList<ActivityModel>,
    val activityLog: MutableList<ActivityLog>
)

class ActivitiesViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseBuilder.getDatabase(application)
    private val dao = database.activitiesDao()
    val activities: LiveData<List<ActivityEntity>> = dao.getAllActivities()
    var activityUIstate by mutableStateOf(
        ActivitiesUIState(
            activities = ActivityModels.allActivities,
            favorites = mutableListOf(),
            activityLog = mutableListOf()
        )
    )
        private set

    // TODO: Lage konstruktør som henter værdata

    fun addFavorite(activity: ActivityModel){

        if (activity in activityUIstate.favorites ){

        }else{
            activityUIstate.favorites.add(activity)
        }
    }

    fun getWeatherData(){
        viewModelScope.launch {
            // TODO: Hent relevant værdata og legg det i kortene

            activityUIstate = activityUIstate.copy(
                activities = mutableListOf()// TODO: Oppdater aktivitetene med værdata i
            )

        }
    }

}