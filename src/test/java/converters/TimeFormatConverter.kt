package converters

import com.charfaouiyounes.TimeFormat
import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.SimpleArgumentConverter
import java.text.SimpleDateFormat

class TimeFormatConverter : SimpleArgumentConverter() {
    override fun convert(source: Any, targetType: Class<*>): Any {
        if (source is String) {
            return when(source){
                "0" -> TimeFormat.TIME_24
                "1" -> TimeFormat.TIME_12
                "2" -> TimeFormat.TIME_12_NO_SUFFIX
                else -> TimeFormat.TIME_FLOATING
            }
        } else {
            throw ArgumentConversionException("Conversion is impossible")
        }
    }
}