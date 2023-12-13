package com.bangkit.stuntcare.ui.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun stringToMediaType(data: String): RequestBody{
    return data.toRequestBody("text/plain".toMediaType())
}