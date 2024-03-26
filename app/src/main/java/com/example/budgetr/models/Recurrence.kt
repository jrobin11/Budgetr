package com.example.budgetr.models

/**
 * Represents the recurrence options for expenses in the Budgetr app. The sealed
 * class allows only subclasses defined here to inherit from Recurrence.
 *
 * @property name The name of the recurrence.
 * @property target A target description associated with the recurrence.
 */
sealed class Recurrence(val name: String, val target: String) {
  object None : Recurrence("None", "None")
  object Daily : Recurrence("Daily", "Today")
  object Weekly : Recurrence("Weekly", "This week")
  object Monthly : Recurrence("Monthly", "This month")
  object Yearly : Recurrence("Yearly", "This year")
}

/**
 * Converts a string representation of a recurrence into a Recurrence enum object.
 *
 * @param this The string representation of the recurrence.
 * @return The Recurrence enum object corresponding to the input string, or Recurrence.None if no match is found.
 */
fun String.toRecurrence(): Recurrence {
  return when(this) {
    "None" -> Recurrence.None
    "Daily" -> Recurrence.Daily
    "Weekly" -> Recurrence.Weekly
    "Monthly" -> Recurrence.Monthly
    "Yearly" -> Recurrence.Yearly
    else -> Recurrence.None
  }
}