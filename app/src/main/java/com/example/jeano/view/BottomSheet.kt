package com.example.jeano.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jeano.R
import com.example.jeano.navigations.Screens
import com.example.jeano.ui.theme.IntroFamily
import com.example.jeano.ui.theme.MidnightDusk_Background
import com.example.jeano.ui.theme.PoppinsFamily
import com.example.jeano.ui.theme.White

@Composable
fun BottomSheetLayout(navController: NavController, isIconModelenabled: Boolean, openBottomSheet: Boolean,
                      onIconModelClick1: () -> Unit, onIconModelClick2: () -> Unit, onIconModelClick3: () -> Unit, OnDevelopersClicked:()-> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightDusk_Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "A.I models",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            fontFamily = IntroFamily,
            color = White)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconModelWithText(image = painterResource(R.drawable.jeano), title = "Jeano", navController = navController,isClickable = isIconModelenabled ){
                onIconModelClick1()
                }
            IconModelWithText(image = painterResource(R.drawable.breiah), title = "Breaih", navController = navController, isClickable = isIconModelenabled ){
                onIconModelClick2()


                }
            IconModelWithText(image = painterResource(R.drawable.lee), title = "Lee", navController = navController, isClickable = isIconModelenabled ){
                onIconModelClick3
                }

            }

        Spacer(modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(2.dp)
            .border(width = 1.dp, color = Color.LightGray))
        Text(
            text = "More",
            color = White,
            fontFamily = IntroFamily,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = "Jeano A.I assistant provides specialized help through three distinct models: Jeano covers general knowledge, Breiah tackles work-related professional insights, and Lee assists with programming queries – and all can converse in Tagalog.",
            fontFamily = PoppinsFamily,
            fontSize = 14.sp,
            color = White,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(2.dp)
            .border(width = 1.dp, color = Color.LightGray))
        Text(
            text = "Go to Developers",
            color = White,
            fontFamily = IntroFamily,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(width = 1.dp, Color(White.value)),
            onClick = OnDevelopersClicked
        ) {
            Text(
                text = "Know more about the Developers",
                color = White
            )
        }


        }

    }

@Preview(showBackground = true)
@Composable
fun PreviewBottomSheet(){
    var openBottomSheet by remember { mutableStateOf(false) }
    var isIconModelenabled by remember { mutableStateOf(true) }
    val navController = rememberNavController()
    BottomSheetLayout(
        navController = navController,
        isIconModelenabled = isIconModelenabled,
        openBottomSheet = openBottomSheet,
        onIconModelClick1 = {

        },
        onIconModelClick2 = {
            isIconModelenabled = false
            openBottomSheet = false
            navController.navigate(Screens.BreaihChatScreen.route) {
                popUpTo(Screens.JeanoChatScreen.route) {
                    inclusive = true
                }
            }
        },
        onIconModelClick3 = {
            isIconModelenabled = false
            openBottomSheet = false
            navController.navigate(Screens.LeeChatScreen.route) {
                popUpTo(Screens.JeanoChatScreen.route) {
                    inclusive = true
                }
            }
        },
        OnDevelopersClicked = {

        }
    )
}