package com.bangkit.stuntcare.ui.model

data class Consultation(
    val members: Member,
    val status: String,
    val message: List<Message>
)

data class Member(
    val parentId: String,
    val doctorId: String
)

data class Message(
    val sender: String,
    val text: String
)
