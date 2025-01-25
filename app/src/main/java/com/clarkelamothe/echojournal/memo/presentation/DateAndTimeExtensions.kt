package com.clarkelamothe.echojournal.memo.presentation

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

fun LocalTime.formatTime(): String = this.format(DateTimeFormatter.ofPattern("HH:mm"))

fun String.toLocalTime(): LocalTime = LocalTime.parse(this)

private val dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d")

fun String.toLocalDate(): LocalDate = LocalDate.parse(this)

fun LocalDate.formatDate(): String {
    return when(this) {
        LocalDate.now() -> "Today"
        LocalDate.now().minusDays(1) -> "Yesterday"
        else -> this.format(dateTimeFormatter)
    }
}

fun Duration.toElapsedTimeFormatted(): String =
    LocalTime.of(0, 0, 0).plusSeconds(this.inWholeSeconds)
        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))

fun Duration.formatDuration(): String = this.toComponents { minutes, seconds, _ ->
    val formattedSec = if (seconds in 0..9) "0$seconds" else seconds

    "$minutes:$formattedSec"
}
