package ru.kpfu.itis.gureva.catcare.utils

import org.joda.time.LocalDate
import org.joda.time.Period
import org.joda.time.format.DateTimeFormat

object Formatter {
    const val DATE_WITHOUT_TIME = "dd.MM.yyyy"

    private fun getDay(count: Long): String {
        return when (count % 10) {
            1L -> "день"
            2L, 3L, 4L -> "дня"
            else -> "дней"
        }
    }

    private fun getMonth(count: Int): String {
        return when (count % 10) {
            1 -> "месяц"
            2, 3, 4 -> "месяца"
            else -> "месяцев"
        }
    }

    private fun getYear(count: Int): String {
        if (count % 10 == 1 && count != 11) {
            return "год"
        }
        else if ((count % 10 in 2..4) && (count < 10 || count > 20)) {
            return "года"
        }
        return "лет"
    }

    fun findDifferenceBetweenDays(date: String?): String {
        val from = LocalDate.parse(date, DateTimeFormat.forPattern(DATE_WITHOUT_TIME))
        val period = Period(from, LocalDate())

        val years = period.years
        val months = period.months
        val days = period.weeks * 7 + period.days

        if (years == 0 && months == 0) {
            return "$days ${getDay(days.toLong())}"
        }
        else if (years == 0) {
            return "$months ${getMonth(months)}"
        }
        else if (months == 0) {
            return "$years ${getYear(years)}"
        }
        else {
            return "$years ${getYear(years)} и $months ${getMonth(months)}"
        }
    }
}
