package com.bangkit.stuntcare.ui.view.parent.consultation.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator

@Composable
fun ChatScreen(
    navigator: ConsultationScreenNavigator
) {
    ChatContent(navigator = navigator)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatContent(
    navigator: ConsultationScreenNavigator,
    modifier: Modifier = Modifier
) {
    Column {
        TopAppBar(
            title = {
                Text(text = "Chat", fontWeight = FontWeight.Medium, fontSize = 20.sp)
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = modifier
                        .size(24.dp)
                        .clickable {
                            navigator.backNavigation()
                        }
                )
            }
        )

        ChatContent(
            navigateToRoomChat = {navigator.navigateToRoomChatScreen()}
        )

        // TODO: Tambahkan Card Chat
//        LazyColumn {
//
//        }
    }
}


@Composable
fun ChatContent(
    navigateToRoomChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigateToRoomChat()
            }
            .height(100.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            AsyncImage(
                model = "", // TODO
                contentDescription = null,
                modifier = modifier.size(width = 65.dp, height = 77.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = "Rizal", fontWeight = FontWeight.Medium, fontSize = 18.sp)
                Text(
                    text = "Last Chat",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}