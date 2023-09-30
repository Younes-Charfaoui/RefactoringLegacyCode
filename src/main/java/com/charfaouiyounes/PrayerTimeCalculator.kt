package com.charfaouiyounes

import java.time.LocalDate

//--------------------- Copyright Block ----------------------
/*

PrayTime.java: Prayer Times Calculator (ver 1.0)
Copyright (C) 2007-2010 PrayTimes.org

Java Code By: Hussain Ali Khan
Original JS Code By: Hamid Zarrabi-Zadeh

License: GNU LGPL v3.0

TERMS OF USE:
	Permission is granted to use this code, with or
	without modification, in any website or application
	provided that credit is given to the original work
	with a link back to PrayTimes.org.

This program is distributed in the hope that it will
be useful, but WITHOUT ANY WARRANTY.

PLEASE DO NOT REMOVE THIS COPYRIGHT BLOCK.

*/

const val INVALID_TIME: String = "-----"
private val DEFAULT_PRAYER_TIMES = PrayerTimes(5.0, 6.0, 12.0, 13.0, 18.0, 18.0, 18.0)

class PrayerTimeCalculator(
    private val calculationMethod: CalculationMethod = CalculationMethod.Jafari,
    private val asrJuristic: AsrJuristic = AsrJuristic.Shafii,
    private val highLatitudesType: HighLatitudesType = HighLatitudesType.MID_NIGHT,
    private val dhuhrMinutes: Int = 0,
    private val timeFormat: TimeFormat = TimeFormat.TIME_24,
    private val offsets: IntArray = IntArray(7) { 0 },
) {

    fun getPrayerTimes(
        date: LocalDate, latitude: Double,
        longitude: Double, timeZone: Double,
    ) = getDatePrayerTimes(date, latitude, longitude, timeZone)

    private fun getDatePrayerTimes(
        date: LocalDate, latitude: Double, longitude: Double, timeZone: Double,
    ) = DEFAULT_PRAYER_TIMES
        .computeTimes(
            latitude = latitude,
            julianDate = date.toJulianDate() - longitude / (15.0 * 24.0),
            parameters = calculationMethod.calculationParameters,
            asrJuristic = asrJuristic
        )
        .adjustTimes(longitude, timeZone, highLatitudesType, calculationMethod.calculationParameters, dhuhrMinutes, offsets)
        .formatWith(timeFormat)
}

