package converters

import com.charfaouiyounes.CalculationMethod
import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.SimpleArgumentConverter

class PrayerTimeCalculationMethodConverter : SimpleArgumentConverter() {
    override fun convert(source: Any, targetType: Class<*>): Any {
        if (source is String) {
            return when(source){
                "0" -> CalculationMethod.Jafari
                "1" -> CalculationMethod.Karachi
                "2" -> CalculationMethod.Isna
                "3" -> CalculationMethod.Mwl
                "4" -> CalculationMethod.Makkah
                "5" -> CalculationMethod.Egypt
                "6" -> CalculationMethod.Tehran
                else -> CalculationMethod.Custom()
            }
        } else {
            throw ArgumentConversionException("Conversion is impossible")
        }
    }
}