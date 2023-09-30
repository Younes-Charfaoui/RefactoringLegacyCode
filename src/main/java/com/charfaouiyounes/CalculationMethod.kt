package com.charfaouiyounes

sealed class CalculationMethod(val calculationParameters: CalculationParameters) {
    object Jafari : CalculationMethod(CalculationParameters(16.0, false, 4.0, false, 14.0))
    object Karachi : CalculationMethod(CalculationParameters(18.0, true, 0.0, false, 18.0))
    object Isna : CalculationMethod(CalculationParameters(15.0, true, 0.0, false, 15.0))
    object Mwl : CalculationMethod(CalculationParameters(18.0, true, 0.0, false, 17.0))
    object Makkah : CalculationMethod(CalculationParameters(18.5, true, 0.0, true, 90.0))
    object Egypt : CalculationMethod(CalculationParameters(19.5, true, 0.0, false, 17.5))
    object Tehran : CalculationMethod(CalculationParameters(17.7, false, 4.5, false, 14.0))
    class Custom(
        fadjerAngle: Double = 18.0,
        isMaghribMinutes: Boolean = true,
        maghribParameter: Double = 0.0,
        isIshaMinutes: Boolean = false,
        ishaParameter: Double = 17.0,
    ) : CalculationMethod(
        CalculationParameters(
            fadjerAngle,
            isMaghribMinutes,
            maghribParameter,
            isIshaMinutes,
            ishaParameter
        )
    )
}

data class CalculationParameters(
    val fadjerAngle: Double,
    val isMaghribMinutes: Boolean,
    val maghribParameter: Double,
    val isIshaMinutes: Boolean,
    val ishaParameter: Double,
) {
    val isIshaAngle = !isIshaMinutes
    val isMaghribAngle = !isMaghribMinutes
}