import com.charfaouiyounes.*
import converters.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvFileSource
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*


private const val LATITUDE = -39.823689
private const val LONGITUDE = 145.121597
private const val TIME_ZONE = 10.0

class PrayerTimeTest {

    @ParameterizedTest
    @CsvFileSource(resources = ["/prayersTimeCases.csv"], numLinesToSkip = 1)
    fun `given multiple input data, assert Time Is Correct`(
        @ConvertWith(TimeFormatConverter::class) timeFormat: TimeFormat,
        @ConvertWith(PrayerTimeCalculationMethodConverter::class) calculationMethod: CalculationMethod,
        @ConvertWith(DateConverter::class) date: LocalDate,
        asrJuristic: Int,
        @ConvertWith(HighLatitudesConverter::class) adjustHighLatitudes: HighLatitudesType,
        latitude: Double,
        longitude: Double,
        timeZone: Double,
        @ConvertWith(PrayerTimeConverter::class) expectedPrayerTimes: FormattedPrayerTimes,
    ) {
        val calculator = PrayerTimeCalculator(
            timeFormat = timeFormat,
            calculationMethod = calculationMethod,
            asrJuristic = asrJuristicFrom(asrJuristic),
            highLatitudesType = adjustHighLatitudes,
            offsets = intArrayOf(0, 0, 0, 0, 0, 0, 0)
        )

        val prayerTime = calculator.getPrayerTimes(date, latitude, longitude, timeZone)

        assertEquals(expectedPrayerTimes, prayerTime)
    }

    @Test
    fun `test case different maghrib and isha angles`() {
        val calculator = PrayerTimeCalculator(
            timeFormat = TimeFormat.TIME_24,
            calculationMethod = CalculationMethod.Custom(
                fadjerAngle = 13.0,
                isMaghribMinutes = false,
                maghribParameter = 40.0,
                isIshaMinutes = false,
                ishaParameter = 45.0
            )
        )

        val prayerTimes = calculator.getPrayerTimes(
            localDateOf(1, 12),
            LATITUDE, LONGITUDE, TIME_ZONE
        )

        assertEquals(
            FormattedPrayerTimes("03:27", "04:46", "12:08", "16:05", "19:31", "00:08", "00:08"),
            prayerTimes
        )
    }

    @Test
    fun `test case different isha minutes`() {
        val calculator = PrayerTimeCalculator(
            timeFormat = TimeFormat.TIME_24,
            calculationMethod = CalculationMethod.Custom(
                ishaParameter = 12.0,
                isIshaMinutes = true
            )
        )

        val prayerTimes = calculator.getPrayerTimes(
            localDateOf(6, 7),
            LATITUDE, LONGITUDE, TIME_ZONE
        )

        assertEquals(
            FormattedPrayerTimes("06:04", "07:41", "12:24", "14:49", "17:08", "17:08", "17:20"),
            prayerTimes
        )
    }

    @Test
    fun `test case different maghrib  minutes`() {
        val calculator = PrayerTimeCalculator(
            timeFormat = TimeFormat.TIME_24,
            calculationMethod = CalculationMethod.Custom(isMaghribMinutes = true, maghribParameter = 13.0)
        )

        val prayerTimes = calculator.getPrayerTimes(
            localDateOf(6, 7),
            LATITUDE, LONGITUDE, TIME_ZONE
        )

        assertEquals(
            FormattedPrayerTimes("06:04", "07:41", "12:24", "14:49", "17:08", "17:21", "18:39"),
            prayerTimes
        )
    }

    @Test
    fun `test case for dhuhrMinutes minutes`() {
        val calculator = PrayerTimeCalculator(
            timeFormat = TimeFormat.TIME_24,
            calculationMethod = CalculationMethod.Makkah,
            dhuhrMinutes = 61,
        )

        val prayerTimes = calculator.getPrayerTimes(
            localDateOf(6, 7),
            LATITUDE, LONGITUDE, TIME_ZONE
        )

        assertEquals(
            FormattedPrayerTimes("06:02", "07:41", "13:24", "14:49", "17:08", "17:08", "18:38"),
            prayerTimes
        )
    }

    @Test
    fun `test case for offsets`() {
        val calculator = PrayerTimeCalculator(
            timeFormat = TimeFormat.TIME_24,
            calculationMethod = CalculationMethod.Makkah,
            dhuhrMinutes = 61,
            offsets = intArrayOf(0, 0, 120, 0, 0, 0, 185)
        )

        val prayerTimes = calculator.getPrayerTimes(
            localDateOf(6, 7),
            LATITUDE, LONGITUDE, TIME_ZONE
        )

        assertEquals(
            FormattedPrayerTimes("06:02", "07:41", "15:24", "14:49", "17:08", "17:08", "21:38"),
            prayerTimes
        )
    }

    private fun localDateOf(day: Int, month: Int): LocalDate {
        val currentYear = LocalDate.now().year
        return LocalDate.of(currentYear, month, day)
    }

    private fun asrJuristicFrom(asrJuristic: Int): AsrJuristic {
        return if (asrJuristic == 0) AsrJuristic.Shafii else AsrJuristic.Hanafi
    }
}