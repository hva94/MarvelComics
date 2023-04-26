package com.hvasoft.androidchallenge.data.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class Date(
    val date: String,
    val type: String
)

fun Date.formattedDate():String{
    val newDate = LocalDateTime
        .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))
        .toLocalDate()
        .format(
            DateTimeFormatter
                .ofLocalizedDate(FormatStyle.LONG)
                .withLocale(Locale.UK)
        )
    return "Release date: $newDate"
}