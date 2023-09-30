package converters

import com.charfaouiyounes.HighLatitudesType
import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.SimpleArgumentConverter

class HighLatitudesConverter : SimpleArgumentConverter() {
    override fun convert(source: Any, targetType: Class<*>): Any {
        if (source is String) {
            return when(source){
                "0" -> HighLatitudesType.NONE
                "1" -> HighLatitudesType.MID_NIGHT
                "2" -> HighLatitudesType.ONE_SEVENTH
                else -> HighLatitudesType.ANGLE_BASED
            }
        } else {
            throw ArgumentConversionException("Conversion is impossible")
        }
    }
}