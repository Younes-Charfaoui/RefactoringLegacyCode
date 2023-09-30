package converters

import com.charfaouiyounes.FormattedPrayerTimes
import org.junit.jupiter.params.converter.ArgumentConversionException
import org.junit.jupiter.params.converter.SimpleArgumentConverter

class PrayerTimeConverter : SimpleArgumentConverter(){
    override fun convert(source: Any?, targetType: Class<*>?): Any {
        if (source is String) {
            val prayerTimes = source.split(",")
            return FormattedPrayerTimes(
                prayerTimes[0],
                prayerTimes[1],
                prayerTimes[2],
                prayerTimes[3],
                prayerTimes[4],
                prayerTimes[5],
                prayerTimes[6]
            )
        } else {
            throw ArgumentConversionException("Conversion is impossible")
        }
    }
}