package com.example.budgetr.components.charts

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.github.tehras.charts.piechart.utils.toLegacyInt
import com.example.budgetr.models.Recurrence
import com.example.budgetr.ui.theme.LabelSecondary

/**
 * Custom LabelDrawer class for rendering labels on a chart.
 *
 * @param recurrence The recurrence type that influences label rendering.
 * @param lastDay The last day of the month (default is -1).
 */
class LabelDrawer(val recurrence: Recurrence, private val lastDay: Int? = -1) :
  com.github.tehras.charts.bar.renderer.label.LabelDrawer {

  // Offset for the left side of labels based on recurrence type.
  private val leftOffset = when (recurrence) {
    Recurrence.Weekly -> 50f
    Recurrence.Monthly -> 13f
    Recurrence.Yearly -> 32f
    else -> 0f
  }

  // Paint for drawing labels.
  private val paint = android.graphics.Paint().apply {
    this.textAlign = android.graphics.Paint.Align.CENTER
    this.color = LabelSecondary.toLegacyInt()
    this.textSize = 42f
  }

  /**
   * Draw a label on the canvas within the specified label area.
   *
   * @param drawScope The drawing scope.
   * @param canvas The canvas to draw on.
   * @param label The label text to be displayed.
   * @param barArea The rectangular area for the corresponding bar.
   * @param xAxisArea The rectangular area for the X-axis.
   */
  override fun drawLabel(
    drawScope: DrawScope,
    canvas: Canvas,
    label: String,
    barArea: Rect,
    xAxisArea: Rect
  ) {
    // Define a condition for monthly labels to be displayed.
    val monthlyCondition =
      recurrence == Recurrence.Monthly && (
              Integer.parseInt(label) % 5 == 0 ||
                      Integer.parseInt(label) == 1 ||
                      Integer.parseInt(label) == lastDay
              )

    // Draw the label on the canvas based on the defined condition.
    if (monthlyCondition || recurrence != Recurrence.Monthly)
      canvas.nativeCanvas.drawText(
        label,
        barArea.left + leftOffset,
        barArea.bottom + 65f,
        paint
      )
  }
}