package com.example.jeano.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // <-- Import dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jeano.R
import com.example.jeano.model.MessageModel
import com.example.jeano.ui.theme.BacksoFamily
import com.example.jeano.ui.theme.Black
import com.example.jeano.ui.theme.ChinaPink
import com.example.jeano.ui.theme.IntroFamily
import com.example.jeano.ui.theme.JeanoTheme
import com.example.jeano.ui.theme.OldMauve
import com.example.jeano.ui.theme.PoppinsFamily
import com.example.jeano.ui.theme.White
import com.example.jeano.viewmodel.JeanoChatViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JeanoChatScreen(
navController: NavController,
    jeanoChatViewModel: JeanoChatViewModel = viewModel()
){
    var question by remember { mutableStateOf("") }
    var isBackButtonEnabled by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 80.dp)
            )
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
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
                title = {
                    Text(
                        text = "Jeano",
                        fontFamily = IntroFamily,
                        color = White
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)

        ) {
            MessageList(
                modifier = Modifier
                    .weight(1f),
                jeanoChatViewModel.messageList)
            TextFieldWithSendButton(
                question = question,
                onValueChange = {
                    question = it
                },
                onSendClick = {
                    if (question.isNotEmpty()) {
                        jeanoChatViewModel.sendQuestion(question)
                        question = ""
                    }else{
                        scope.launch {
                            snackbarHostState.showSnackbar("Question cannot be empty.")
                        }
                    }
                }
            )

        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>){
    if (messageList.isEmpty()){
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.notext),
                contentDescription = "No text"
            )
            Text(
                text = "Start a conversation",
                fontFamily = BacksoFamily,
                color = OldMauve
            )
        }
    }else{
        LazyColumn(
            modifier = modifier,
            reverseLayout = true

        ) {
            items(messageList.reversed()) {
                MessageRow(it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel){
    val isModel = messageModel.role == "model"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(start = if (isModel) 8.dp else 70.dp, end = if (isModel) 70.dp else 8.dp, top = 8.dp, bottom = 8.dp)
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) ChinaPink else OldMauve)
                    .padding(16.dp)
            ){
                Text(
                    text = messageModel.message,
                    fontWeight = FontWeight.W500,
                    fontFamily = PoppinsFamily,
                    color = White
                )
            }
        }

    }
}

@Composable
fun TextFieldWithSendButton(question : String, onValueChange:(String)-> Unit, onSendClick:()->Unit) {
    var focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(OldMauve),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            value = question,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                cursorColor = White
            ),
            textStyle = TextStyle(
                fontFamily = PoppinsFamily,
                color = White
            ),
            placeholder = {
                Text(
                    text = "Type your questions here...",
                    fontFamily = PoppinsFamily,
                    color = White
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    onSendClick()
                    focusManager.clearFocus()
                }
            )
        )
        IconButton(
            onClick = onSendClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Send,
                contentDescription = "Send",
                tint = White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJeanoChatScreen(){
    JeanoTheme {
        val navController = rememberNavController()
        JeanoChatScreen(navController)
    }
}