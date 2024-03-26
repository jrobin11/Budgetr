package com.example.budgetr.utils

import com.example.budgetr.models.Recurrence
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * Formats the LocalDate into a user-friendly day representation.
 *
 * @return A formatted string representing the date (e.g., "Today," "Yesterday," or "Mon, 01 Jan 2023").
 */
fun LocalDate.formatDay(): String {
  val today = LocalDate.now()
  val yesterday = today.minusDays(1)

  return when {
    this.isEqual(today) -> "Today"
    this.isEqual(yesterday) -> "Yesterday"
    this.year != today.year -> this.format(DateTimeFormatter.ofPattern("ddd, dd MMM yyyy"))
    else -> this.format(DateTimeFormatter.ofPattern("E, dd MMM"))
  }
}

/**
 * Formats the LocalDateTime into a user-friendly day representation for date range views.
 *
 * @return A formatted string representing the date (e.g., "01 Jan 2023" or "01 Jan").
 */
fun LocalDateTime.formatDayForRange(): String {
  val today = LocalDateTime.now()
  val yesterday = today.minusDays(1)

  return when {
    this.year != today.year -> this.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    else -> this.format(DateTimeFormatter.ofPattern("dd MMM"))
  }
}

/**
 * Represents data about a date range including the start date, end date, and days in range.
 */
data class DateRangeData(
  val start: LocalDate,
  val end: LocalDate,
  val daysInRange: Int
)

/**
 * Calculates the date range based on the recurrence and page for date range views.
 *
 * @param recurrence The recurrence frequency.
 * @param page The page number within the date range view.
 * @return A [DateRangeData] object representing the calculated date range.
 */
fun calculateDateRange(recurrence: Recurrence, page: Int): DateRangeData {
  val today = LocalDate.now()
  lateinit var start: LocalDate
  lateinit var end: LocalDate
  var daysInRange = 7

  when (recurrence) {
    Recurrence.Daily -> {
      start = LocalDate.now().minusDays(page.toLong())
      end = start
    }
    Recurrence.Weekly -> {
      start =
        LocalDate.now().minusDays(today.dayOfWeek.value.toLong() - 1)
          .minusDays((page * 7).toLong())
      end = start.plusDays(6)
      daysInRange = 7
    }
    Recurrence.Monthly -> {
      start =
        LocalDate.of(today.year, today.month, 1)
          .minusMonths(page.toLong())
      val numberOfDays =
        YearMonth.of(start.year, start.month).lengthOfMonth()
      end = start.plusDays(numberOfDays.toLong())
      daysInRange = numberOfDays
    }
    Recurrence.Yearly -> {
      start = LocalDate.of(today.year, 1, 1)
      end = LocalDate.of(today.year, 12, 31)
      daysInRange = 365
    }
    else -> Unit
  }

  return DateRangeData(
    start = start,
    end = end,
    daysInRange = daysInRange
  )
}