package com.example.jeano.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jeano.R
import com.example.jeano.model.DevelopersData
import com.example.jeano.ui.theme.IntroFamily
import com.example.jeano.ui.theme.MidnightDusk_Background
import com.example.jeano.ui.theme.MidnightDusk_GreenAccent
import com.example.jeano.ui.theme.MidnightDusk_GreyElement
import com.example.jeano.ui.theme.MidnightDusk_LightGrey
import com.example.jeano.ui.theme.MidnightDusk_PinkAccent
import com.example.jeano.ui.theme.PoppinsFamily
import com.example.jeano.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevelopersScreen(navController: NavController){
    var isBackButtonEnabled by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val developersList = listOf(
        DevelopersData(profile = painterResource(R.drawable.kenley_profile), name = "Kenley Russe Benitez", role = "Lead Developer", description = "Android Developer"),
        DevelopersData(profile = painterResource(R.drawable.breiah_profile), name = "Breiah Mendavia", role = "Team Leader", description = "Android Developer"),
        DevelopersData(profile = painterResource(R.drawable.jeano_profile), name = "Jeano Genuino", role = "UI/UX Designer", description = "Android Developer"),
        DevelopersData(profile = painterResource(R.drawable.dean_profile), name = "Dean Aynslue Garcia", role = "Junior Developer", description = "Android Developer"),
        DevelopersData(profile = painterResource(R.drawable.christian_profile), name = "Christian Soberano", role = "UI/UX Assistant", description = "Android Developer"),
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Developers",
                        fontFamily = IntroFamily,
                        color = White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MidnightDusk_Background
                ),
                navigationIcon = {
                    IconButton(
                        enabled = isBackButtonEnabled,
                        onClick = {
                            isBackButtonEnabled = false
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = "back icon", tint = White, modifier = Modifier.size(100.dp))
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MidnightDusk_Background)
                .padding(it)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            items(developersList) {
                DevelopersItemHolder(it, navController)
            }
        }
    }

}
@Composable
fun DevelopersItemHolder(developersData: DevelopersData, navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MidnightDusk_GreyElement
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .size(130.dp)
                    .weight(2f),
                painter = developersData.profile,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "profile"
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f),
            ){
                Text(
                    text = developersData.name,
                    fontFamily = PoppinsFamily,
                    fontSize = 20.sp,
                    color = White
                )
                Text(
                    text = developersData.role,
                    fontFamily = IntroFamily,
                    color = MidnightDusk_GreenAccent
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewDev(){
    val navController = rememberNavController()
    val dev = DevelopersData(profile = painterResource(R.drawable.breiah_profile), name = "Kenley Russe Benitez", role = "Lead Developer", description = "Android Developer")
    DevelopersItemHolder(dev, navController)
}