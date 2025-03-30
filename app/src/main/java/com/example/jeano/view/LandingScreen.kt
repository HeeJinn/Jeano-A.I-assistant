package com.example.jeano.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jeano.R
import com.example.jeano.navigations.Screens
import com.example.jeano.ui.theme.BacksoFamily
import com.example.jeano.ui.theme.ChinaPink
import com.example.jeano.ui.theme.LavenderBlush
import com.example.jeano.ui.theme.OldMauve
import com.example.jeano.ui.theme.PoppinsFamily
import com.example.jeano.ui.theme.White

@Composable
fun LandingScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Jeano",
            fontFamily = BacksoFamily,
            color = Color(White.value),
            fontSize = 60.sp
        )
        Text(
            text = "Your A.I assistant",
            fontFamily = PoppinsFamily,
            color = Color(White.value),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id= R.drawable.brain),
            contentDescription = "jeano"
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            modifier = Modifier
                .width(250.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(LavenderBlush.value),
                contentColor = Color(White.value),
            ),
            border = null,
            onClick = {
                navController.navigate(Screens.JeanoChatScreen.route)
            },
        ) {
            Text(
                text = "Start Chatting",
                fontFamily = PoppinsFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(ChinaPink.value)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    val navController = rememberNavController()
    LandingScreen(navController)
}

