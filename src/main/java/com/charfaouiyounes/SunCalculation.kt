package com.charfaouiyounes

import java.time.LocalDate
import kotlin.math.floor

fun equationOfTime(julianDate: Double): Double {
    val (q, l, e) = calculateSunParams(julianDate)
    val ra = fixHourRange(
        degreeAtan2(degreeCos(e) * degreeSin(l), degreeCos(l)) / 15.0
    )
    return q / 15.0 - ra
}

fun sunDeclination(julianDate: Double): Double {
    val (_, l, e) = calculateSunParams(julianDate)
    return degreeAsin(degreeSin(e) * degreeSin(l))
}

private fun calculateSunParams(julianDate: Double): Triple<Double, Double, Double> {
    val d = julianDate - 2451545
    val g = fixAngle(357.529 + 0.98560028 * d)
    val q = fixAngle(280.459 + 0.98564736 * d)
    val l = fixAngle(q + 1.915 * degreeSin(g) + 0.020 * degreeSin(2 * g))
    val e = 23.439 - 0.00000036 * d
    return Triple(q, l, e)
}

fun LocalDate.toJulianDate(): Double {
    var year = year
    var month = month.value
    if (month <= 2) {
        year -= 1
        month += 12
    }
    val a = floor(year / 100.0)
    val b = 2 - a + floor(a / 4.0)
    return (floor(365.25 * (year + 4716)) + floor(30.6001 * (month + 1)) + dayOfMonth + b) - 1524.5
}


fun fixHourRange(hour: Double): Double {
    var a = hour
    a -= 24.0 * floor(a / 24.0)
    a = if (a < 0) a + 24 else a
    return a
}

fun fixAngle(angle: Double): Double {
    var a = angle
    a -= 360 * floor(a / 360.0)
    a = if (a < 0) a + 360 else a
    return a
}