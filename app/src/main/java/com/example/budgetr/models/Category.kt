package com.example.budgetr.models

import androidx.compose.ui.graphics.Color
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * Represents a category for expenses in the Budgetr app. Categories have a name and a color.
 *
 * @constructor Creates an empty Category with default values.
 */
class Category() : RealmObject {
  @PrimaryKey
  var _id: ObjectId = ObjectId.create()

  private var _colorValue: String = "0,0,0"
  var name: String = ""

  /**
   * The color property of the Category. It returns a Color object based on the stored color value.
   */
  val color: Color
    get() {
      val colorComponents = _colorValue.split(",")
      val (red, green, blue) = colorComponents
      return Color(red.toFloat(), green.toFloat(), blue.toFloat())
    }

  /**
   * Creates a Category with the specified name and color.
   *
   * @param name The name of the category.
   * @param color The color of the category.
   */
  constructor(
    name: String,
    color: Color
  ) : this() {
    this.name = name
    this._colorValue = "${color.red},${color.green},${color.blue}"
  }
}