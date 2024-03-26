package com.example.budgetr.utils

import java.text.DecimalFormat

/**
 * Simplifies a numeric value into a more readable string representation with K (thousands) or M (millions) suffixes.
 *
 * @param value The numeric value to simplify.
 * @return A string representing the simplified value (e.g., "1.5K" for 1500, "2.3M" for 2300000).
 */
fun simplifyNumber(value: Float): String {
  return when {
    value >= 1000 && value < 1_000_000 -> DecimalFormat("0.#K").format(value / 1000)
    value >= 1_000_000 -> DecimalFormat("0.#M").format(value / 1_000_000)
    else -> DecimalFormat("0.#").format(value)
  }
}