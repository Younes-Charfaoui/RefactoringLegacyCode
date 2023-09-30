package com.charfaouiyounes

import kotlin.math.floor
import kotlin.math.roundToInt

private const val ROUNDING_FACTOR = 0.5

fun Double.to24HoursFormat(): String {
    if (isNaN()) return INVALID_TIME
    val time = fixHourRange(this + ROUNDING_FACTOR / 60.0)
    val hours = floor(time).toInt()
    val minutes = floor((time - hours) * 60.0)
    return formatTime(hours, minutes)
}

private fun formatTime(hours: Int, minutes: Double, suffix : String = "") = when {
    hours.isOneDigit() && minutes.isOneDigit() -> "0$hours:0${minutes.roundToInt()} $suffix"
    hours.isOneDigit() -> "0$hours:${minutes.roundToInt()} $suffix"
    minutes.isOneDigit() -> "$hours:0${minutes.roundToInt()} $suffix"
    else -> "$hours:${minutes.roundToInt()} $suffix"
}.trim()

private fun Double.to12HourFormat(noSuffix: Boolean): String {
    if (isNaN()) return INVALID_TIME
    val time = fixHourRange(this + ROUNDING_FACTOR / 60)
    var hours = floor(time).toInt()
    val minutes = floor((time - hours) * 60)
    val suffix = if (hours >= 12) "pm" else "am"
    hours = (hours + 12 - 1) % 12 + 1
    return formatTime(hours, minutes, if (noSuffix) "" else suffix)
}


fun Double.to12HourFormatWithoutSuffix(): String {
    return this.to12HourFormat(true)
}

fun Double.to12HourFormat(): String {
    return this.to12HourFormat(false)
}

private fun Double.isOneDigit() = this in 0.0..9.0

private fun Int.isOneDigit() = this in 0..9