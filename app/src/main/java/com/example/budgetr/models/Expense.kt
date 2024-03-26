package com.example.budgetr.models

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * Represents an expense in the Budgetr app. Expenses have attributes such as amount, recurrence, date, note, and category.
 *
 * @constructor Creates an empty Expense with default values.
 */
class Expense(): RealmObject {
  @PrimaryKey
  var _id: ObjectId = ObjectId.create()
  var amount: Double = 0.0

  private var _recurrenceName: String = "None"

  /**
   * The recurrence property of the Expense. It returns the Recurrence enum based on the stored recurrence name.
   */
  val recurrence: Recurrence get() { return _recurrenceName.toRecurrence() }

  private var _dateValue: String = LocalDateTime.now().toString()

  /**
   * The date property of the Expense. It returns a LocalDateTime object based on the stored date value.
   */
  val date: LocalDateTime get() { return LocalDateTime.parse(_dateValue) }

  var note: String = ""
  var category: Category? = null

  /**
   * Creates an Expense with the specified attributes.
   *
   * @param amount The amount of the expense.
   * @param recurrence The recurrence of the expense.
   * @param date The date of the expense.
   * @param note The note or description of the expense.
   * @param category The category to which the expense belongs.
   */
  constructor(
    amount: Double,
    recurrence: Recurrence,
    date: LocalDateTime,
    note: String,
    category: Category,
  ) : this() {
    this.amount = amount
    this._recurrenceName = recurrence.name
    this._dateValue = date.toString()
    this.note = note
    this.category = category
  }
}

/**
 * Represents a group of expenses for a specific day along with the total expense amount.
 *
 * @property expenses A list of expenses for the day.
 * @property total The total amount of expenses for the day.
 */
data class DayExpenses(
  val expenses: MutableList<Expense>,
  var total: Double,
)

/**
 * Groups a list of expenses by day and calculates the total expenses for each day.
 *
 * @return A map where keys are LocalDates representing days, and values are DayExpenses containing expenses and total amount.
 */
fun List<Expense>.groupedByDay(): Map<LocalDate, DayExpenses> {
  val dataMap: MutableMap<LocalDate, DayExpenses> = mutableMapOf()

  this.forEach { expense ->
    val date = expense.date.toLocalDate()

    if (dataMap[date] == null) {
      dataMap[date] = DayExpenses(
        expenses = mutableListOf(),
        total = 0.0
      )
    }

    dataMap[date]!!.expenses.add(expense)
    dataMap[date]!!.total = dataMap[date]!!.total.plus(expense.amount)
  }

  dataMap.values.forEach { dayExpenses ->
    dayExpenses.expenses.sortBy { expense -> expense.date }
  }

  return dataMap.toSortedMap(compareByDescending { it })
}

/**
 * Groups a list of expenses by day of the week and calculates the total expenses for each day of the week.
 *
 * @return A map where keys are day names, and values are DayExpenses containing expenses and total amount.
 */
fun List<Expense>.groupedByDayOfWeek(): Map<String, DayExpenses> {
  val dataMap: MutableMap<String, DayExpenses> = mutableMapOf()

  this.forEach { expense ->
    val dayOfWeek = expense.date.toLocalDate().dayOfWeek

    if (dataMap[dayOfWeek.name] == null) {
      dataMap[dayOfWeek.name] = DayExpenses(
        expenses = mutableListOf(),
        total = 0.0
      )
    }

    dataMap[dayOfWeek.name]!!.expenses.add(expense)
    dataMap[dayOfWeek.name]!!.total = dataMap[dayOfWeek.name]!!.total.plus(expense.amount)
  }

  return dataMap.toSortedMap(compareByDescending { it })
}

/**
 * Groups a list of expenses by day of the month and calculates the total expenses for each day of the month.
 *
 * @return A map where keys are day of the month, and values are DayExpenses containing expenses and total amount.
 */
fun List<Expense>.groupedByDayOfMonth(): Map<Int, DayExpenses> {
  val dataMap: MutableMap<Int, DayExpenses> = mutableMapOf()

  this.forEach { expense ->
    val dayOfMonth = expense.date.toLocalDate().dayOfMonth

    if (dataMap[dayOfMonth] == null) {
      dataMap[dayOfMonth] = DayExpenses(
        expenses = mutableListOf(),
        total = 0.0
      )
    }

    dataMap[dayOfMonth]!!.expenses.add(expense)
    dataMap[dayOfMonth]!!.total = dataMap[dayOfMonth]!!.total.plus(expense.amount)
  }

  return dataMap.toSortedMap(compareByDescending { it })
}

/**
 * Groups a list of expenses by month and calculates the total expenses for each month.
 *
 * @return A map where keys are month names, and values are DayExpenses containing expenses and total amount.
 */
fun List<Expense>.groupedByMonth(): Map<String, DayExpenses> {
  val dataMap: MutableMap<String, DayExpenses> = mutableMapOf()

  this.forEach { expense ->
    val month = expense.date.toLocalDate().month

    if (dataMap[month.name] == null) {
      dataMap[month.name] = DayExpenses(
        expenses = mutableListOf(),
        total = 0.0
      )
    }

    dataMap[month.name]!!.expenses.add(expense)
    dataMap[month.name]!!.total = dataMap[month.name]!!.total.plus(expense.amount)
  }

  return dataMap.toSortedMap(compareByDescending { it })
}