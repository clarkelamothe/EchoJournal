package com.clarkelamothe.echojournal.memo.presentation

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

fun String.formatTime(): String =
    LocalTime.parse(this).format(DateTimeFormatter.ofPattern("HH:mm"))

fun String.formatDate(): String {
    val date = LocalDate.parse(this)
    val today = LocalDate.now()

    return when (date) {
        today -> "Today"
        today.minusDays(1) -> "Yesterday"
        else -> date.format(dateTimeFormatter)
    }
}

private val dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d")
fun String.toLocalDate(): LocalDate = LocalDate.parse(this, dateTimeFormatter)

fun Duration.toElapsedTimeFormatted(): String =
    LocalTime.of(0, 0, 0).plusSeconds(this.inWholeSeconds)
        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
