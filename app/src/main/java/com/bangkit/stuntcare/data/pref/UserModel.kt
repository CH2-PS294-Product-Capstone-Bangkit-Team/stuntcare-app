package com.bangkit.stuntcare.data.pref

data class UserModel (
    val id: String,
    val email: String,
    val role: String,
    val token: String,
    val isLogin: Boolean
)