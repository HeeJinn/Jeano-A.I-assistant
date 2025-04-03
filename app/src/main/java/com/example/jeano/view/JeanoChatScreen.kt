package com.example.jeano.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Edit
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
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // <-- Import dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.util.TableInfo
import com.example.jeano.MyApp
import com.example.jeano.R
import com.example.jeano.model.MessageModel
import com.example.jeano.navigations.Screens
import com.example.jeano.ui.theme.BacksoFamily
import com.example.jeano.ui.theme.Black
import com.example.jeano.ui.theme.ChinaPink
import com.example.jeano.ui.theme.GreenAppleAccentLightGreen
import com.example.jeano.ui.theme.IntroFamily
import com.example.jeano.ui.theme.JeanoTheme
import com.example.jeano.ui.theme.MidnightDusk_Background
import com.example.jeano.ui.theme.MidnightDusk_GreenAccent
import com.example.jeano.ui.theme.MidnightDusk_GreyElement
import com.example.jeano.ui.theme.MidnightDusk_PinkAccent
import com.example.jeano.ui.theme.OldMauve
import com.example.jeano.ui.theme.PoppinsFamily
import com.example.jeano.ui.theme.White
import com.example.jeano.viewmodel.JeanoChatViewModel
import com.example.jeano.viewmodel.JeanoChatViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JeanoChatScreen(
navController: NavController,
){
    val application = LocalContext.current.applicationContext as MyApp
    val jeanoChatViewModel : JeanoChatViewModel =viewModel(factory = JeanoChatViewModelFactory(application.repository))
    val messageList = jeanoChatViewModel.allMessages.collectAsStateWithLifecycle()

    var openBottomSheet by remember { mutableStateOf(false) }
    var isActionIconEnabled by remember { mutableStateOf(true) }
    var bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var isIconModelenabled by remember { mutableStateOf(true) }

    val isTyping by jeanoChatViewModel.isTyping.collectAsStateWithLifecycle()
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
                    containerColor = Color(MidnightDusk_Background.value)
                ),
                navigationIcon = {
                    IconButton(
                        enabled = isBackButtonEnabled,
                        onClick = {
                            isBackButtonEnabled = false
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = "back icon", tint = MidnightDusk_PinkAccent, modifier = Modifier.size(100.dp))
                    }
                },
                title = {
                    Text(
                        text = "Jeano",
                        fontFamily = IntroFamily,
                        color = MidnightDusk_PinkAccent,
                    )
                },
                actions = {
                    IconButton(
                        enabled = isActionIconEnabled,
                        onClick = {
                            isActionIconEnabled = false
                            openBottomSheet = true

                        }
                    ) {Icon(imageVector = Icons.Rounded.Face, contentDescription = "ai_models", tint = MidnightDusk_PinkAccent) }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MidnightDusk_Background)
                .padding(paddingValues)

        ) {
            MessageList(
                modifier = Modifier
                    .weight(1f),
                messageList.value,
                isTyping)

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
    //Logic sa bottomsheet
    if (openBottomSheet){
        ModalBottomSheet(
            modifier = Modifier,
            containerColor = MidnightDusk_Background,
            onDismissRequest = {
                openBottomSheet = false
                isActionIconEnabled = true
            },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(MidnightDusk_Background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                BottomSheetLayout(
                    navController = navController,
                    isIconModelenabled = isIconModelenabled,
                    openBottomSheet = openBottomSheet,
                    onIconModelClick1 = {

                    },
                    onIconModelClick2 = {
                        isIconModelenabled = false
                        openBottomSheet = false
                        Log.d("BOTTOM_SHEET_ICON", "$isIconModelenabled $openBottomSheet")
                        navController.navigate(Screens.BreaihChatScreen.route){
                            popUpTo(Screens.JeanoChatScreen.route){
                                inclusive = true
                            }
                        }
                    },
                    onIconModelClick3 = {
                        isIconModelenabled = false
                        openBottomSheet = false
                        Log.d("BOTTOM_SHEET_ICON", "$isIconModelenabled $openBottomSheet")
                        navController.navigate(Screens.LeeChatScreen.route){
                            popUpTo(Screens.JeanoChatScreen.route){
                                inclusive = true
                            }
                        }
                    },
                    OnDevelopersClicked = {
                        openBottomSheet = false
                        navController.navigate(Screens.DevelopersScreen.route){
                            popUpTo(Screens.JeanoChatScreen.route){
                                inclusive = true
                            }
                        }
                    }


                )

            }
        }
    }
}

@Composable
fun IconModelWithText(image: Painter, title: String, navController: NavController, isClickable: Boolean, onIconModelClick:()->Unit){
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                enabled = isClickable,
                onClick = onIconModelClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = "profiles",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),

        )
        Text(
            text = title,
            fontFamily = PoppinsFamily,
            color = White,
            fontSize = 18.sp
        )

    }

}


@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messageList: List<MessageModel>,
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
                color = White, // Your custom color
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
                                .background(MidnightDusk_GreenAccent) // Model background color
                                .padding(horizontal = 12.dp, vertical = 8.dp) // Inner padding
                        ) {
                            Text(
                                text = "Jeano is typing...", // The indicator text
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
                MessageRow(message) // Pass only the message model
            }
        }
    }
}


@Composable
fun MessageRow(messageModel: MessageModel){
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
                    .background(if (isModel) MidnightDusk_GreenAccent else MidnightDusk_PinkAccent)
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
fun TextFieldWithSendButton(question : String, onValueChange:(String)-> Unit, onSendClick:()->Unit) {
    var focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MidnightDusk_Background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isTextFieldFocused = focusState.isFocused
                },
            value = question,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MidnightDusk_PinkAccent,
                unfocusedBorderColor = MidnightDusk_PinkAccent,
                cursorColor = MidnightDusk_PinkAccent
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
        if (question.isEmpty()) {
            IconButton(
                onClick = {
                    if (isTextFieldFocused) focusManager.clearFocus()
                    else focusRequester.requestFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "edit",
                    tint = MidnightDusk_PinkAccent
                )
            }
        }else{
            IconButton(
                onClick = onSendClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    contentDescription = "Send",
                    tint = MidnightDusk_PinkAccent
                )
            }
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