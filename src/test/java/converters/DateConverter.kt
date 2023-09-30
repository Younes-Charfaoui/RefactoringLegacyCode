package converters

import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.SimpleArgumentConverter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId

class DateConverter : SimpleArgumentConverter() {
    override fun convert(source: Any, targetType: Class<*>): Any {
        if (source is String) {
            val format = SimpleDateFormat("dd/MM")
            return LocalDate.ofInstant(format.parse(source).toInstant(), ZoneId.systemDefault())
        } else {
            throw ArgumentConversionException("Conversion is impossible")
        }
    }
}