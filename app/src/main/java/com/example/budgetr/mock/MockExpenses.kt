package com.example.budgetr.mock

import androidx.compose.ui.graphics.Color
import com.example.budgetr.models.Category
import com.example.budgetr.models.Expense
import com.example.budgetr.models.Recurrence
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * A utility class that generates mock data for testing or development purposes in the Budgetr app.
 */
val faker = Faker()

/**
 * A list of mock categories for expenses, each with a name and a randomly generated color.
 */
val mockCategories = listOf(
  Category(
    "Bills",
    Color(
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255)
    )
  ),
  Category(
    "Subscriptions", Color(
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255)
    )
  ),
  Category(
    "Take out", Color(
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255)
    )
  ),
  Category(
    "Hobbies", Color(
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255),
      faker.random.nextInt(0, 255)
    )
  ),
)

/**
 * A list of mock expenses, each with random attributes like amount, date, recurrence, note, and category.
 */
val mockExpenses: List<Expense> = List(30) {
  Expense(
    amount = faker.random.nextInt(min = 1, max = 999)
      .toDouble() + faker.random.nextDouble(),
    date = LocalDateTime.now().minus(
      faker.random.nextInt(min = 300, max = 345600).toLong(),
      ChronoUnit.SECONDS
    ),
    recurrence = faker.random.randomValue(
      listOf(
        Recurrence.None,
        Recurrence.Daily,
        Recurrence.Monthly,
        Recurrence.Weekly,
        Recurrence.Yearly
      )
    ),
    note = faker.australia.animals(),
    category = faker.random.randomValue(mockCategories)
  )
}