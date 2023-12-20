package com.bangkit.stuntcare.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun dateToDay(birthDate: String): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val birth = dateFormat.parse(birthDate)

    val todayDate = Calendar.getInstance().time
    val day = todayDate.time - birth.time

    return TimeUnit.MILLISECONDS.toDays(day).toInt()
}

fun convertDateAndLongToAge(dateBegin: String, dateEnd: Long): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date1 = dateFormat.parse(dateBegin) ?: return -1

    val date2 = Date(dateEnd)

    val diffInMills = date2.time - date1.time

    return (diffInMills / (24 * 60 * 60 * 1000)).toInt()
}

fun convertLongToDateString(timestamp: Long): String {
    val date = Date(timestamp)
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(date)
}