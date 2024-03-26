package com.example.budgetr.components.charts

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.github.tehras.charts.bar.BarChartData
import com.example.budgetr.models.Recurrence
import com.example.budgetr.ui.theme.SystemGray04

/**
 * Custom BarDrawer class for rendering bars in a bar chart.
 *
 * @param recurrence The recurrence type that influences bar rendering.
 */
class BarDrawer constructor(recurrence: Recurrence) :
  com.github.tehras.charts.bar.renderer.bar.BarDrawer {
  private val barPaint = Paint().apply {
    this.isAntiAlias = true
  }

  // Offset for the right side of the bars based on recurrence type.
  private val rightOffset = when(recurrence) {
    Recurrence.Weekly -> 24f
    Recurrence.Monthly -> 6f
    Recurrence.Yearly -> 18f
    else -> 0f
  }

  /**
   * Draw a bar on the canvas within the specified bar area.
   *
   * @param drawScope The drawing scope.
   * @param canvas The canvas to draw on.
   * @param barArea The rectangular area for the bar.
   * @param bar The bar data to be rendered.
   */
  override fun drawBar(
    drawScope: DrawScope,
    canvas: Canvas,
    barArea: Rect,
    bar: BarChartData.Bar
  ) {
    // Draw a gray background bar.
    canvas.drawRoundRect(
      barArea.left,
      0f,
      barArea.right + rightOffset,
      barArea.bottom,
      16f,
      16f,
      barPaint.apply {
        color = SystemGray04
      },
    )

    // Draw the colored bar on top of the gray background.
    canvas.drawRoundRect(
      barArea.left,
      barArea.top,
      barArea.right + rightOffset,
      barArea.bottom,
      16f,
      16f,
      barPaint.apply {
        color = bar.color
      },
    )
  }
}