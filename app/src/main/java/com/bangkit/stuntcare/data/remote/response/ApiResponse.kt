package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String
)