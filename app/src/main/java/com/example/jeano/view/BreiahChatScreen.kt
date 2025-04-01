package com.example.jeano.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jeano.MyApp
import com.example.jeano.R
import com.example.jeano.model.BreiahMessageModel
import com.example.jeano.model.MessageModel
import com.example.jeano.navigations.Screens
import com.example.jeano.ui.theme.BacksoFamily
import com.example.jeano.ui.theme.ChinaPink
import com.example.jeano.ui.theme.GreenAppleAccentLightGreen
import com.example.jeano.ui.theme.GreenAppleAccentPink
import com.example.jeano.ui.theme.GreenAppleBackground
import com.example.jeano.ui.theme.IntroFamily
import com.example.jeano.ui.theme.MidnightDusk_Background
import com.example.jeano.ui.theme.MidnightDusk_PinkAccent
import com.example.jeano.ui.theme.OldMauve
import com.example.jeano.ui.theme.PoppinsFamily
import com.example.jeano.ui.theme.White
import com.example.jeano.viewmodel.JeanoChatViewModel
import com.example.jeano.viewmodel.JeanoChatViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreiahChatScreen(navController: NavController){
    val application = LocalContext.current.applicationContext as MyApp
    val jeanoChatViewModel : JeanoChatViewModel =viewModel(factory = JeanoChatViewModelFactory(application.repository))
    val messageList = jeanoChatViewModel.allBreaihMessages.collectAsStateWithLifecycle()

    var openBottomSheet by remember { mutableStateOf(false) }
    var isActionIconEnabled by remember { mutableStateOf(true) }
    var bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var isIconModelenabled by remember { mutableStateOf(true) }

    val isTyping by jeanoChatViewModel.breiahIsTyping.collectAsStateWithLifecycle()


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
                    containerColor = GreenAppleBackground
                ),
                navigationIcon = {
                    IconButton(
                        enabled = isBackButtonEnabled,
                        onClick = {
                            isBackButtonEnabled = false
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = "back icon", tint = GreenAppleAccentLightGreen, modifier = Modifier.size(100.dp))
                    }
                },
                title = {
                    Text(
                        text = "Breiah",
                        fontFamily = IntroFamily,
                        color = GreenAppleAccentLightGreen
                    )
                },
                actions = {
                    IconButton(
                        enabled = isActionIconEnabled,
                        onClick = {
                            isActionIconEnabled = false
                            openBottomSheet = true

                        }
                    ) {Icon(imageVector = Icons.Rounded.Face, contentDescription = "ai_models", tint = GreenAppleAccentLightGreen) }
                }
            )
        }
    ){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GreenAppleBackground)
                .padding(paddingValues)

        ){
            BreiahMessageList(
                modifier = Modifier
                    .weight(1f),
                messageList.value,
                isTyping)

            BreiahTextFieldWithSendButton(
                question = question,
                onValueChange = {
                    question = it
                },
                onSendClick = {
                    if (question.isNotEmpty()) {
                        jeanoChatViewModel.sendBreaihQuestion(question)
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
    //Logic sa bottomsheet
    if (openBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                openBottomSheet = false
                isActionIconEnabled = true
            },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "A.I models",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    textAlign = TextAlign.Start,
                    fontFamily = IntroFamily)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconModelWithText(image = painterResource(R.drawable.jeano), title = "Jeano", navController = navController,isClickable = isIconModelenabled ){
                        isIconModelenabled = false
                        navController.navigate(Screens.JeanoChatScreen.route){
                            popUpTo(Screens.BreaihChatScreen.route){
                                inclusive = true
                            }
                        }
                    }
                    IconModelWithText(image = painterResource(R.drawable.breiah), title = "Breaih", navController = navController, isClickable = isIconModelenabled ){
                        isIconModelenabled = false



                    }
                    IconModelWithText(image = painterResource(R.drawable.lee), title = "Lee", navController = navController, isClickable = isIconModelenabled ){
                        isIconModelenabled = false
                        openBottomSheet = false
                        navController.navigate(Screens.LeeChatScreen.route){
                            popUpTo(Screens.BreaihChatScreen.route){
                                inclusive = true
                            }
                        }


                    }

                }
            }
        }
    }
}
@Composable
fun BreiahMessageList(
    modifier: Modifier = Modifier,
    messageList: List<BreiahMessageModel>,
    isTyping: Boolean
    // listState: LazyListState // Keep this if you added scrolling logic
) {
    // Show empty state only if the list is empty AND the model isn't typing
    if (messageList.isEmpty() && !isTyping) {
        Column(
            modifier = modifier.fillMaxSize(), // Use the modifier containing weight
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.notext), // Ensure this drawable exists
                modifier = Modifier.size(150.dp).padding(bottom = 16.dp),
                contentDescription = "No messages yet"
            )
            Text(
                text = "Start a conversation",
                fontFamily = BacksoFamily, // Your custom font
                color = GreenAppleAccentLightGreen, // Your custom color
                style = MaterialTheme.typography.bodyLarge // Use theme typography
            )
        }
    } else {
        // Display the list of messages and potentially the typing indicator
        LazyColumn(
            modifier = modifier.padding(horizontal = 8.dp), // Apply modifier and padding
            reverseLayout = true, // New items appear at the bottom, scrolls from bottom
            // state = listState // Pass state if using scrolling logic
        ) {
            // --- Conditionally Display Typing Indicator FIRST in code ---
            // So reverseLayout places it at the BOTTOM visually (index 0)
            if (isTyping) {
                item { // Typing indicator is conceptually the 'first' item
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp), // Padding around typing indicator
                        horizontalArrangement = Arrangement.Start // Model always types from start
                    ) {
                        Box(
                            modifier = Modifier
                                // Styling similar to a model message bubble
                                .padding(
                                    start = 8.dp,
                                    end = 70.dp, // Asymmetric padding
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
                                .clip(RoundedCornerShape(16.dp))
                                .background(GreenAppleAccentPink) // Model background color
                                .padding(horizontal = 12.dp, vertical = 8.dp) // Inner padding
                        ) {
                            Text(
                                text = "Breiah is typing...", // The indicator text
                                fontWeight = FontWeight.W500,
                                fontFamily = PoppinsFamily,
                                color = White, // Changed back to White for consistency with bubbles

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp)) // Space after typing indicator
                }
            }

            // --- Display Actual Messages AFTER typing indicator in code ---
            // These will appear visually ABOVE the typing indicator
            items(messageList.reversed()) { message ->
                BreiahMessageRow(message) // Pass only the message model
            }
        }
    }
}
@Composable
fun BreiahMessageRow(messageModel: BreiahMessageModel){
    var isModel = messageModel.role == "model"
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
                    .clip(if (isModel) RoundedCornerShape(topStart = 10f, topEnd = 48f, bottomStart = 48f, bottomEnd = 48f) else RoundedCornerShape(topStart = 48f, topEnd = 10f, bottomStart = 48f, bottomEnd = 48f))
                    .background(if (isModel) GreenAppleAccentPink else GreenAppleAccentLightGreen)
                    .padding(16.dp)
            ){
                SelectionContainer {
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
}
@Composable
fun BreiahTextFieldWithSendButton(question : String, onValueChange:(String)-> Unit, onSendClick:()->Unit) {
    var focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GreenAppleBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            value = question,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenAppleAccentLightGreen,
                unfocusedBorderColor = GreenAppleAccentLightGreen,
                cursorColor = GreenAppleAccentLightGreen
            ),
            shape = RoundedCornerShape(100f),
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
                tint = GreenAppleAccentLightGreen
            )
        }
    }
}
