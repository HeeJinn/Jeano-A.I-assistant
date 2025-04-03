package com.example.jeano.view

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jeano.R
import com.example.jeano.navigations.Screens
import com.example.jeano.ui.theme.BacksoFamily
import com.example.jeano.ui.theme.ChinaPink
import com.example.jeano.ui.theme.IntroFamily
import com.example.jeano.ui.theme.LavenderBlush
import com.example.jeano.ui.theme.MidnightDusk_Background
import com.example.jeano.ui.theme.MidnightDusk_GreyElement
import com.example.jeano.ui.theme.MidnightDusk_PinkAccent
import com.example.jeano.ui.theme.OldMauve
import com.example.jeano.ui.theme.PoppinsFamily
import com.example.jeano.ui.theme.White


@Composable

fun LandingScreen(navController: NavController){
    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as? Activity
    BackHandler {
        openDialog = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(MidnightDusk_Background.value)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Jeano",
            fontFamily = BacksoFamily,
            color = Color(MidnightDusk_PinkAccent.value),
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
                containerColor = Color(MidnightDusk_PinkAccent.value),

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
                color = Color(White.value)
            )
        }

    }
    if (openDialog){
        CustomExitDialog(
            onDismiss = {
                openDialog = false
            },
            onConfirm = {
                activity?.finishAffinity()
            }
        )
    }
}

@Composable
fun CustomExitDialog(onDismiss:()-> Unit, onConfirm:()-> Unit){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(48f),
            colors = CardDefaults.cardColors(
                containerColor = MidnightDusk_Background
            )

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    color = MidnightDusk_PinkAccent
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .fillMaxWidth(),
                        text = "Exit App",
                        textAlign = TextAlign.Start,
                        fontFamily = IntroFamily,
                        color = White,
                        fontSize = 30.sp
                    )
                }
                Spacer(Modifier.height(30.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    text = "Are you sure you want to exit?",
                    fontFamily = PoppinsFamily,
                    color = White,
                    fontSize = 18.sp

                )
                Image(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .size(140.dp),
                    painter = painterResource(id = R.drawable.exit_sticker),
                    contentDescription = "exit"
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) { Text(
                        text = "Cancel",
                        fontFamily = PoppinsFamily,
                        color = White,
                        fontSize = 20.sp
                    )
                    }
                    TextButton(
                        onClick = onConfirm
                    ) { Text(
                        text = "Exit",
                        fontFamily = PoppinsFamily,
                        color = White,
                        fontSize = 20.sp
                    )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialog(){
    CustomExitDialog(
        onDismiss = {

        },
        onConfirm = {

        }
    )
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    val navController = rememberNavController()
    LandingScreen(navController)
}

