package io.backoffice.common.model.type

import io.backoffice.common.model.constants.DateConstants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * The enum Local date formatter type.
 */
enum class LocalDateFormatterType(
    pattern: String
) : Formatter<LocalDate, String>, Parser<String, LocalDate> {
    /**
     * The Yyyy mm dd without delimiter.
     * e.g. 20230123
     */
    YYYY_MM_DD_WITHOUT_DELIMITER(DateConstants.YYYY_MM_DD_WITHOUT_DELIMITER);

    /**
     * Gets date time formatter.
     *
     * @return the date time formatter
     */
    val dateTimeFormatter: DateTimeFormatter

    init {
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    }

    override fun format(t: LocalDate): String {
        return dateTimeFormatter.format(t)
    }

    override fun parse(t: String): LocalDate {
        return LocalDate.parse(t)
    }
}
