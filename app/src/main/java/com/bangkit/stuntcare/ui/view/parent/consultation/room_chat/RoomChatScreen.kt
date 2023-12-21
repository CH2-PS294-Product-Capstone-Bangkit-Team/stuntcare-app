package com.bangkit.stuntcare.ui.view.parent.consultation.room_chat

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.component.buuble_chat.BubbleChatFromSender
import com.bangkit.stuntcare.ui.component.buuble_chat.BubbleChatReceiver
import com.bangkit.stuntcare.ui.model.chat.Chat
import com.bangkit.stuntcare.ui.navigation.navigator.ConsultationScreenNavigator
import com.bangkit.stuntcare.ui.utils.DataDummy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RoomChatScreen(
    navigator: ConsultationScreenNavigator,
    modifier: Modifier = Modifier
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val chatCollection = db.collection("chat")
    val messageCollection = db.collection("message")
    LazyColumn() {
        stickyHeader {
            TopAppBar(
                title = {
                    Row {
                        AsyncImage(
                            model = "",
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = modifier
                                .clip(CircleShape)
                                .size(44.dp)
                        )

                        Column {
                            Text(
                                text = "Dr. Nasurllah",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Online",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                },
                navigationIcon = {
                    navigator.backNavigation()
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            )
        }
        items(DataDummy.dummyChat.first().messages, { it.messageId }) {
            if (it.senderId == "udin") {
                BubbleChatFromSender(text = it.text)
            } else {
                BubbleChatReceiver(text = it.text, image = "")
            }
        }
    }

}
