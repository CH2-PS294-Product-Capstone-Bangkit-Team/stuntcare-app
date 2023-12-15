package com.bangkit.stuntcare.ui.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun checkPermission(typePermission: String, context: Context): Boolean{
    return ContextCompat.checkSelfPermission(context, typePermission) == PackageManager.PERMISSION_GRANTED
}