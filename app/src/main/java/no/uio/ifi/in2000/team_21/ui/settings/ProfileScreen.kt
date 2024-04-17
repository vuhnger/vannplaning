package no.uio.ifi.in2000.team_21.ui.settings

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.team_21.R
import no.uio.ifi.in2000.team_21.Screen
import no.uio.ifi.in2000.team_21.ui.theme.backgroundLight
import no.uio.ifi.in2000.team_21.ui.theme.onContainerLight
import no.uio.ifi.in2000.team_21.ui.theme.profileLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen (navController: NavController) {

    val FUNCTION_NAME = object {}.javaClass.enclosingMethod.name
    
    val notification = rememberSaveable { mutableStateOf("") }

    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
        
    }

    Log.d(
        FUNCTION_NAME,
        "called"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profil",
                        color = onContainerLight
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
                            tint = onContainerLight,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(backgroundLight)
            )
        },
        containerColor = backgroundLight
    ) {innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
                .fillMaxWidth()
        ){
            EditProfileImage()
            //Save button
            OutlinedButton(
                onClick = {
                    notification.value = "Profil oppdatert"
                }
            ) {
                Text("Lagre")
            }
        }
    }
}


@Composable
fun EditProfileImage(){
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
        elevation = cardElevation(
            pressedElevation = 30.dp
        ),
        colors = CardColors(
            contentColor = profileLight,
            containerColor = profileLight,
            disabledContainerColor = profileLight,
            disabledContentColor = profileLight
        ),
        modifier = Modifier
            .size(60.dp)
            .clickable { }
    ){
        Image(painter = painter,
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .padding(15.dp)
                .size(30.dp)
                .clip(CircleShape),
                //.clickable { },
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ProfileScreenTest() {
    ProfileScreen(navController = rememberNavController())
}