package com.bangkit.stuntcare.ui.utils

import java.math.BigDecimal

fun removeTrailingZeros(value: Float): String {
    val trimmedValue = BigDecimal(value.toString()).stripTrailingZeros()
    return if (trimmedValue.scale() > 0) {
        trimmedValue.toPlainString()
    } else {
        trimmedValue.toInt().toString()
    }
}