package com.charfaouiyounes

enum class HighLatitudesType {
    NONE,
    MID_NIGHT,
    ONE_SEVENTH,
    ANGLE_BASED;

    fun nightPortion(angle: Double) = when (this) {
        NONE -> 0.0
        MID_NIGHT -> 0.5
        ONE_SEVENTH -> 0.14286
        ANGLE_BASED -> angle / 60.0
    }
}