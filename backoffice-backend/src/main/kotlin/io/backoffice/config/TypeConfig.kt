package io.backoffice.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.backoffice.common.model.type.LocalDateFormatterType
import io.backoffice.common.model.type.LocalDateTimeFormatterType
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class TypeConfig : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToLocalDateConverter())
        registry.addConverter(StringToLocalDateTimeConverter())
    }

    class StringToLocalDateConverter : Converter<String, LocalDate> {
        override fun convert(source: String): LocalDate {
            return try {
                LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE)
            } catch (e: Exception) {
                LocalDate.parse(
                    source,
                    LocalDateFormatterType.YYYY_MM_DD_WITHOUT_DELIMITER.dateTimeFormatter
                )
            }
        }
    }

    class StringToLocalDateTimeConverter : Converter<String, LocalDateTime> {
        override fun convert(source: String): LocalDateTime {
            return try {
                LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            } catch (e: Exception) {
                LocalDateTime.parse(
                    source,
                    LocalDateTimeFormatterType.YYYY_MM_DD_HH_MM_SS_DELIMITER_DASH_AND_COLON.dateTimeFormatter
                )
            }
        }
    }

    @Bean
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.deserializers(multipleLocalDateTimeDeserializer())
            builder.deserializers(multipleLocalDateDeserializer())
        }
    }

    @Bean
    fun multipleLocalDateTimeDeserializer(): JsonDeserializer<LocalDateTime> {
        return object : JsonDeserializer<LocalDateTime>() {
            @Throws(IOException::class)
            override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
                return parseLocalDateTimeMultipleFormat(p.text)
            }

            override fun handledType(): Class<LocalDateTime> {
                return LocalDateTime::class.java
            }
        }
    }

    @Bean
    fun multipleLocalDateDeserializer(): JsonDeserializer<LocalDate> {
        return object : JsonDeserializer<LocalDate>() {

            @Throws(IOException::class)
            override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDate {
                return parseLocalDateMultipleFormat(p.text)
            }

            override fun handledType(): Class<LocalDate> {
                return LocalDate::class.java
            }
        }
    }

    private fun parseLocalDateTimeMultipleFormat(str: String): LocalDateTime {
        return try {
            LocalDateTime.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: Exception) {
            LocalDateTime.parse(
                str,
                LocalDateTimeFormatterType.YYYY_MM_DD_HH_MM_SS_DELIMITER_DASH_AND_COLON.dateTimeFormatter
            )
        }
    }

    private fun parseLocalDateMultipleFormat(str: String): LocalDate {
        return try {
            LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            LocalDate.parse(
                str,
                LocalDateFormatterType.YYYY_MM_DD_WITHOUT_DELIMITER.dateTimeFormatter
            )
        }
    }
}