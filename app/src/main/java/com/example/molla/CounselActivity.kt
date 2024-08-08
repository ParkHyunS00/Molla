package com.example.molla

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.molla.ui.theme.MollaTheme
import java.text.SimpleDateFormat
import java.util.*

class CounselActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { CounselActivityContent() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounselActivityContent() {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("d일", Locale.getDefault())
    val dayOfMonth = dateFormat.format(currentDate) // 현재 날짜 가져옴

    var userInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val chatMessageList = remember {
        mutableStateListOf(
            ChatMessageUiModel("자신의 감정을 솔직하게 털어놔 보세요. 어제 등산은 잘 갔다 오셨나요?", false, writer = "AI 상담사"),
            ChatMessageUiModel("짧은글", true, writer = "사용자"),
            ChatMessageUiModel("자신의 감정을 솔직하게 털어놔 보세요. 어제 상담 상담 상담 상담 상담 상담", false, writer = "AI 상담사"),
            ChatMessageUiModel("자신의 감정을 솔직하게 털어놔 보세요. 어제 상담 상담 상담 상담 상담 상담", false, writer = "AI 상담사"),
            ChatMessageUiModel("safasfdasfsafsdafsdafsadfsafsafsafsafsdafsfsafsfsasafsafsadfsad", true, writer = "사용자"),
            ChatMessageUiModel("safasfdasfsafsdafsdafsadfsafsafsafsafsdafsfsafsfsasafsafsadfsad", true, writer = "사용자"))
    }

    MollaTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("상담") },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn (
                    modifier = Modifier.weight(1f),
                    state = listState
                ) {
                    item {
                        Text(
                            text = "2024년 7월",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            LabeledHorizontalDivider(label = dayOfMonth)
                        }
                    }
                    items(
                        items = chatMessageList,
                    ) {
                        chatMessage -> ChatBubble(chatMessage = chatMessage)
                    }
                }
                // 스크롤 상태에서 채팅 입력시 자동으로 하단 이동
                LaunchedEffect(chatMessageList.size) {
                    listState.animateScrollToItem(chatMessageList.size - 1)
                }

                Text(
                    text = "MOLLA의 AI 상담사는 의학적 사실을 검증하지 않습니다. 사용에 주의가 필요합니다.",
                    color = Color.Gray,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                ChatInputSection(
                    userInput = userInput,
                    onUserInputChange = { userInput = it },
                    onSendMessage = {
                        if (userInput.isNotEmpty()) {
                            // TODO : 서버로 채팅 입력 내용 전송
                            chatMessageList.add(ChatMessageUiModel(userInput, true, writer = "사용자"))
                            userInput = ""

                            // TODO : 서버로 받은 응답 값(AI 상담사의 답변) 또한 리스트에 추가
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounselActivityPreview() {
    CounselActivityContent()
}