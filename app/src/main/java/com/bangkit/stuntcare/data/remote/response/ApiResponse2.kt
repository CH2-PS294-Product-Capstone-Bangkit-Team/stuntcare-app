package com.bangkit.stuntcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class ApiResponse2(
    @field:SerializedName("error")
    val status: Boolean,

    @field:SerializedName("message")
    val message: String
)