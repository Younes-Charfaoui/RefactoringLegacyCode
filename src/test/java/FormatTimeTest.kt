import com.charfaouiyounes.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FormatTimeTest {

    @Test
    fun `test converting time to 12h`() {

        Assertions.assertEquals(
            12.5.to24HoursFormat(),
            "12:30"
        )
        Assertions.assertEquals(
            16.5.to12HourFormatWithoutSuffix(),
            "04:30"
        )
        Assertions.assertEquals(
            17.5.to12HourFormat(),
            "05:30 pm"
        )
        Assertions.assertEquals(
            Double.NaN.to12HourFormat(),
            INVALID_TIME
        )

        Assertions.assertEquals(
            Double.NaN.to24HoursFormat(),
            INVALID_TIME
        )
    }
}
