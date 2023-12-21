package com.bangkit.stuntcare.ui.model.chat

import java.sql.Timestamp

data class Chat(
    val chatId: String,
    val participant: List<String>,
    val messages: List<Message>
)

data class Message(
    val messageId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long
)