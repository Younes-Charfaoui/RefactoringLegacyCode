package converters

import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.SimpleArgumentConverter

class ListConverter : SimpleArgumentConverter(){
    override fun convert(source: Any?, targetType: Class<*>?): Any {
        if (source is String) {
            return source.split(",")
        } else {
            throw ArgumentConversionException("Conversion is impossible")
        }
    }
}