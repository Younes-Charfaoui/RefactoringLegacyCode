package com.charfaouiyounes

import kotlin.math.abs

internal data class PrayerTimes(
    val fajr: Double,
    val sunrise: Double,
    val dhuhr: Double,
    val asr: Double,
    val sunset: Double,
    val maghrib: Double,
    val isha: Double
) {

    fun computeTimes(
        latitude: Double,
        julianDate: Double,
        parameters: CalculationParameters,
        asrJuristic: AsrJuristic,
    ): PrayerTimes {
        val times = toDayPortion()
        val fajr = computeTime(180 - parameters.fadjerAngle, times.fajr, latitude, julianDate)
        val sunrise = computeTime(180 - 0.833, times.sunrise, latitude, julianDate)
        val dhuhr = computeMidDay(times.dhuhr, julianDate)
        val asr = computeAsr(asrJuristic, times.asr, latitude, julianDate)
        val sunset = computeTime(0.833, times.sunset, latitude, julianDate)
        val maghrib = computeTime(parameters.maghribParameter, times.maghrib, latitude, julianDate)
        val isha = computeTime(parameters.ishaParameter, times.isha, latitude, julianDate)
        return PrayerTimes(fajr, sunrise, dhuhr, asr, sunset, maghrib, isha)
    }

    private fun toDayPortion() = PrayerTimes(
        fajr = fajr / 24.0,
        sunrise = sunrise / 24.0,
        dhuhr = dhuhr / 24.0,
        asr = asr / 24.0,
        sunset = sunset / 24.0,
        maghrib = maghrib / 24.0,
        isha = isha / 24.0
    )

    private fun computeTime(angle: Double, time: Double, latitude: Double, julianDate: Double): Double {
        val declination = sunDeclination(julianDate + time)
        val z = computeMidDay(time, julianDate)
        val beg = -degreeSin(angle) - degreeSin(declination) * degreeSin(latitude)
        val mid = degreeCos(declination) * degreeCos(latitude)
        val v = degreeAcos(beg / mid) / 15.0
        return z + if (angle > 90) -v else v
    }

    private fun computeMidDay(aTime: Double, julianDate: Double): Double {
        val time = equationOfTime(julianDate + aTime)
        return fixHourRange(12 - time)
    }

    private fun computeAsr(step: AsrJuristic, time: Double, latitude: Double, julianDate: Double): Double {
        val declination = sunDeclination(julianDate + time)
        val g = -degreeAcot(step.shadowLength + degreeTan(abs(latitude - declination)))
        return computeTime(g, time, latitude, julianDate)
    }

    private fun adjustHighLatitudes(
        highLatitudesType: HighLatitudesType,
        parameters: CalculationParameters,
    ) = if (highLatitudesType != HighLatitudesType.NONE) adjustHighLatTimes(highLatitudesType, parameters) else this

    fun adjustTimes(
        longitude: Double,
        timeZone: Double,
        highLatitudesType: HighLatitudesType,
        parameters: CalculationParameters,
        dhuhrMinutes: Int,
        offsets: IntArray,
    ) = adjust(timeZone, longitude).adjustDhuhr(dhuhrMinutes)
        .adjustForMaghrib(parameters)
        .adjustForIsha(parameters)
        .adjustHighLatitudes(highLatitudesType, parameters)
        .tuneTimesWith(offsets)

    private fun adjust(timeZone: Double, longitude: Double) = PrayerTimes(
        fajr = fajr + timeZone - longitude / 15,
        sunrise = sunrise + timeZone - longitude / 15,
        dhuhr = dhuhr + timeZone - longitude / 15,
        asr = asr + timeZone - longitude / 15,
        sunset = sunset + timeZone - longitude / 15,
        maghrib = maghrib + timeZone - longitude / 15,
        isha = isha + timeZone - longitude / 15
    )

    private fun adjustForMaghrib(parameters: CalculationParameters) =
        copy(maghrib = if (parameters.isMaghribMinutes) sunset + parameters.maghribParameter / 60 else maghrib)

    private fun adjustForIsha(parameters: CalculationParameters) =
        copy(isha = if (parameters.isIshaMinutes) maghrib + parameters.ishaParameter / 60 else isha)

    private fun adjustHighLatTimes(
        highLatitudesType: HighLatitudesType,
        parameters: CalculationParameters,
    ): PrayerTimes {
        val nightTime = timeDiff(sunset, sunrise)

        val fajrDiff = highLatitudesType.nightPortion(parameters.fadjerAngle) * nightTime
        val newFajr = if (fajr.isNaN() || timeDiff(fajr, sunrise) > fajrDiff) sunrise - fajrDiff else fajr

        val maghribAngle = if (parameters.isMaghribAngle) parameters.maghribParameter else 4.0
        val maghribDiff = highLatitudesType.nightPortion(maghribAngle) * nightTime
        val newMaghrib = if (maghrib.isNaN() || timeDiff(sunset, maghrib) > maghribDiff) sunset + maghribDiff else maghrib

        val ishaAngle = if (parameters.isIshaAngle) parameters.ishaParameter else 18.0
        val ishaDiff = highLatitudesType.nightPortion(ishaAngle) * nightTime
        val newIsha = if (isha.isNaN() || timeDiff(sunset, isha) > ishaDiff) sunset + ishaDiff else isha

        return copy(fajr = newFajr, maghrib = newMaghrib, isha = newIsha)
    }

    private fun timeDiff(time1: Double, time2: Double) = fixHourRange(time2 - time1)

    private fun adjustDhuhr(dhuhrMinutes: Int) = copy(dhuhr = dhuhr + (dhuhrMinutes / 60).toDouble())

    private fun tuneTimesWith(timeOffsets: IntArray) = PrayerTimes(
        fajr = fajr + timeOffsets[0] / 60,
        sunrise = sunrise + timeOffsets[1] / 60,
        dhuhr = dhuhr + timeOffsets[2] / 60,
        asr = asr + timeOffsets[3] / 60,
        sunset = sunset + timeOffsets[4] / 60,
        maghrib = maghrib + timeOffsets[5] / 60,
        isha = isha + timeOffsets[6] / 60
    )

    fun formatWith(timeFormat: TimeFormat) = FormattedPrayerTimes(
        fajr = timeFormat.formatTime(fajr),
        sunrise = timeFormat.formatTime(sunrise),
        dhuhr = timeFormat.formatTime(dhuhr),
        asr = timeFormat.formatTime(asr),
        sunset = timeFormat.formatTime(sunset),
        maghrib = timeFormat.formatTime(maghrib),
        isha = timeFormat.formatTime(isha)
    )
}