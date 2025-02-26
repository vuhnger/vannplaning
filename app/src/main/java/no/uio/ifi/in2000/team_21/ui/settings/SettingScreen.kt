package no.uio.ifi.in2000.team_21.ui.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import no.uio.ifi.in2000.team_21.R
import no.uio.ifi.in2000.team_21.Screen
import no.uio.ifi.in2000.team_21.ui.theme.Background
import no.uio.ifi.in2000.team_21.ui.theme.ContainerBlue
import no.uio.ifi.in2000.team_21.ui.theme.MidnightBlue
import no.uio.ifi.in2000.team_21.ui.theme.White
import no.uio.ifi.in2000.team_21.ui.viewmodels.UserViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun SettingScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {

    val FUNCTION_NAME = object {}.javaClass.enclosingMethod.name

    Log.d(
        FUNCTION_NAME,
        "called"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Innstillinger",
                        color = MidnightBlue
                    )
                },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                contentDescription = "Tilbake",
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                tint = MidnightBlue,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        } },
                colors = TopAppBarDefaults.topAppBarColors(Background),
            )},
        containerColor = Background,
    ){
            innerPadding->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            // Bakgrunnsbilde for skjermen
            Image(
                painter = painterResource(id = R.drawable.waterbackground),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .scale(1.2f)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .padding(innerPadding)
            ){
                //Profile
                ProfileCard(
                    navController,
                    userViewModel = userViewModel,
                    onClick = { navController.navigate(Screen.ProfileScreen.route)}
                )
                Spacer(modifier = Modifier.padding(6.dp))

                //All activities
                AllSettingsCard(navController,
                    mainText = "Alle aktiviteter",
                    onClick = {
                        navController.navigate(Screen.AllActivitiesScreen.route)
                    }
                )
                Spacer(modifier = Modifier.padding(6.dp))

                //History
                HistorySettings(navController)
                Spacer(modifier = Modifier.padding(6.dp))


                AllSettingsCard(navController,
                    mainText = "Om utviklerne",
                    onClick = {
                        navController.navigate(Screen.AboutUsScreen.route)
                    }
                )
                Spacer(modifier = Modifier.padding(6.dp))
                AllSettingsCard(navController,
                    mainText = "Logg ut",
                    onClick = {
                        userViewModel.logOut()
                    },
                )
            }
        }

    }
}




//Profile
@Composable
fun ProfileCard(
    navController: NavController,
    onClick: () -> Unit,
    userViewModel: UserViewModel
){
    val currentUser = userViewModel.currentUser.observeAsState()

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors (White)

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 10.dp, bottom = 10.dp)
        ) {
            ProfileImage()
            Column {
                //sende med navn fra profileScreen
                Text(
                    modifier = Modifier
                        .width(190.dp)
                        .padding(bottom = 5.dp, start = 15.dp),
                    text = currentUser.value?.name ?: "Standardbruker",

                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = MidnightBlue,
                        letterSpacing = 0.1.sp
                    )
                )
                //sende med hobby fra profileScreen
                Text(
                    modifier = Modifier
                        .width(190.dp)
                        .padding(start = 15.dp),
                    text = currentUser.value?.hobby ?: "Standardhobby",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = MidnightBlue,
                        letterSpacing = 0.1.sp
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                contentDescription = "Min profil",
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = MidnightBlue
            )
        }
    }
}

//All settings-cards
@Composable
fun AllSettingsCard(navController: NavController, mainText: String, onClick:()->Unit){
    Card(
        onClick = onClick,
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(56.dp),
        colors = CardDefaults.cardColors(White)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 15.dp, bottom = 15.dp)
        ) {
            Text(
                modifier = Modifier
                    .width(190.dp),
                text = mainText,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(500),
                    color = MidnightBlue,
                    letterSpacing = 0.1.sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                contentDescription = mainText,
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = MidnightBlue
            )
        }
    }
}

//History settings
//Ikke helt happy med hvordan kortene ligge over hverandre, kommer tydelig frem når man klikker på dem
@Composable
fun HistorySettings(navController: NavController){
    Card(
        modifier = Modifier
            //.clickable { }
            .fillMaxWidth()
            .height(56.dp),
        colors = CardDefaults.cardColors(White)
    ){ Column (

    ){
        //Me
        AllSettingsCard(navController,
            mainText = "Historikk",
            onClick = {
                navController.navigate(Screen.MyActivityScreen.route)
            }
        )
        HorizontalDivider(
            color = ContainerBlue,
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp)
        )
    }
    }
}


//Other settings
@Composable
fun SettingsGroupCard(navController: NavController){
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(56.dp),
        colors = CardDefaults.cardColors(White)
    ){Column{
        //Notifications
        AllSettingsCard(navController,
            mainText = "Varslinger",
            onClick = {
                navController.navigate(Screen.NotificationScreen.route)
            }
        )
    } }
}


@Composable
fun ProfileImage(){
    val image = rememberSaveable {
        mutableStateOf("")
    }
    val painter = if(image.value.isEmpty()){
        painterResource(id = R.drawable.user)
    } else{
        painterResource(id = image.value.toInt())
    }
    
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(MidnightBlue),
        modifier = Modifier
            .size(60.dp)
    ){
        Image(painter = painter,
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .padding(15.dp)
                .size(30.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}


@Preview
@Composable
fun SettingScreenTest() {
    //SettingScreen(navController = rememberNavController())
}