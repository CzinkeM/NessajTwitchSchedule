package com.github.czinkem.nessaj_twitch_schedule.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateHelper {

    object Week {
        fun getCurrentWeek(): Pair<LocalDate, LocalDate>  {
            val currentClock = Clock.System.now()
            val currentDate =currentClock.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val startOfWeek = currentDate.minus(currentDate.dayOfWeek.ordinal, DateTimeUnit.DAY)
            val endOfWeek = currentDate.plus(6 - currentDate.dayOfWeek.ordinal, DateTimeUnit.DAY)
            return startOfWeek to endOfWeek
        }

        fun getNextWeek(endOfWeek: LocalDate): Pair<LocalDate, LocalDate> {
            val startOfNextWeek = endOfWeek.plus(1, DateTimeUnit.DAY)
            val endOfNextWeek = endOfWeek.plus(1, DateTimeUnit.WEEK)
            return startOfNextWeek to endOfNextWeek
        }

        fun getPreviousWeek(startOfWeek: LocalDate): Pair<LocalDate, LocalDate> {
            val endOfPreviousWeek = startOfWeek.minus(1, DateTimeUnit.DAY)
            val startOfPreviousWeek = startOfWeek.minus(1, DateTimeUnit.WEEK)
            return startOfPreviousWeek to endOfPreviousWeek
        }
    }

    fun isTimePassed(date: LocalDateTime): Boolean {
        return date.toInstant(TimeZone.currentSystemDefault()) < Clock.System.now()
    }

    fun isStreamLive(startDate: LocalDateTime, endDate: LocalDateTime): Boolean {
        return isTimePassed(startDate) && !isTimePassed(endDate)
    }

    fun localDateTimeToHourMinuteString(localDateTime: LocalDateTime): String {
        val hour = if (localDateTime.hour < 10) "0${localDateTime.hour}" else "${localDateTime.hour}"
        val minute = if (localDateTime.minute < 10) "0${localDateTime.minute}" else "${localDateTime.minute}"
        return "$hour:$minute"
    }
}