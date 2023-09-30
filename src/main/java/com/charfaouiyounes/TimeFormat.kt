package com.charfaouiyounes

enum class TimeFormat {
    TIME_12 {
        override fun formatTime(prayerTime: Double) = prayerTime.to12HourFormat()
    },
    TIME_24 {
        override fun formatTime(prayerTime: Double) = prayerTime.to24HoursFormat()
    },
    TIME_12_NO_SUFFIX {
        override fun formatTime(prayerTime: Double) = prayerTime.to12HourFormatWithoutSuffix()
    },
    TIME_FLOATING {
        override fun formatTime(prayerTime: Double) = prayerTime.toString()
    };


    abstract fun formatTime(prayerTime: Double): String
}