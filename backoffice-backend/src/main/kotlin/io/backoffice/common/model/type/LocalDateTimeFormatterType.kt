package io.backoffice.common.model.type

import io.backoffice.common.model.constants.DateConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * The enum Local date time formatter type.
 */
enum class LocalDateTimeFormatterType(
    pattern: String
) : Formatter<LocalDateTime, String>, Parser<String, LocalDateTime> {
    /**
     * The Yyyy mm dd delimiter dot.
     * e.g. 2023.01.23
     */
    YYYY_MM_DD_DELIMITER_DOT(DateConstants.YYYY_MM_DD_DELIMITER_DOT),

    /**
     * The Dd mm hh ss.
     * e.g. 23113059
     */
    DD_MM_HH_SS_WITHOUT_DELIMITER(DateConstants.DD_MM_HH_SS_WITHOUT_DELIMITER),

    /**
     * The Yyyy mm dd hh mm ss delimiter dot and colon.
     * e.g. 2021-11-31 23:59:59
     */
    YYYY_MM_DD_HH_MM_SS_DELIMITER_DASH_AND_COLON(DateConstants.YYYY_MM_DD_HH_MM_SS_DELIMITER_DASH_AND_COLON);

    /**
     * Gets date time formatter.
     *
     * @return the date time formatter
     */
    val dateTimeFormatter: DateTimeFormatter

    init {
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    }

    override fun format(t: LocalDateTime): String {
        return dateTimeFormatter.format(t)
    }

    override fun parse(t: String): LocalDateTime {
        return LocalDateTime.parse(t)
    }
}
