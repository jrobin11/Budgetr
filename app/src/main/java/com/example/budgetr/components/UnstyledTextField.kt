package com.example.budgetr.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.budgetr.ui.theme.Primary
import com.example.budgetr.ui.theme.TextPrimary

/**
 * A customizable unstyled text field for input.
 *
 * This composable function provides an unstyled text field for user input with various customization options.
 *
 * @param value The current input value.
 * @param onValueChange A callback to handle changes in the input value.
 * @param modifier The modifier for the text field.
 * @param enabled Whether the text field is enabled for user interaction.
 * @param readOnly Whether the text field is in read-only mode.
 * @param textStyle The text style for the text field.
 * @param label A composable for rendering a label.
 * @param placeholder A composable for rendering a placeholder.
 * @param arrangement The arrangement of components within the text field.
 * @param leadingIcon A composable for rendering a leading icon.
 * @param trailingIcon A composable for rendering a trailing icon.
 * @param supportingText A composable for additional supporting text.
 * @param isError Whether the text field is in an error state.
 * @param visualTransformation The visual transformation to apply to the text.
 * @param keyboardOptions The keyboard options for the text field.
 * @param keyboardActions The keyboard actions for the text field.
 * @param singleLine Whether the text field is limited to a single line.
 * @param maxLines The maximum number of lines in the text field.
 * @param interactionSource The interaction source for the text field.
 * @param shape The shape of the text field.
 * @param colors The colors for the text field.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnstyledTextField(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  textStyle: TextStyle = LocalTextStyle.current,
  label: @Composable (() -> Unit)? = null,
  placeholder: @Composable (() -> Unit)? = null,
  arrangement: Arrangement.Horizontal = Arrangement.Start,
  leadingIcon: @Composable (() -> Unit)? = null,
  trailingIcon: @Composable (() -> Unit)? = null,
  supportingText: @Composable (() -> Unit)? = null,
  isError: Boolean = false,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  singleLine: Boolean = false,
  maxLines: Int = Int.MAX_VALUE,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  shape: Shape = TextFieldDefaults.filledShape,
  colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
  // If color is not provided via the text style, use content color as a default
  val textColor = TextPrimary
  val mergedTextStyle =
    textStyle.merge(TextStyle(color = textColor))

  BasicTextField(value = value,
    onValueChange = onValueChange,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = mergedTextStyle,
    cursorBrush = SolidColor(Primary),
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    interactionSource = interactionSource,
    singleLine = singleLine,
    maxLines = maxLines,
    decorationBox = @Composable { innerTextField ->
      // The TextFieldDecorationBox composable is used for customizing the appearance of the text field.
      TextFieldDefaults.TextFieldDecorationBox(
        value = value,
        visualTransformation = visualTransformation,
        innerTextField = innerTextField,
        placeholder = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = arrangement,
            verticalAlignment = CenterVertically
          ) {
            placeholder?.invoke()
          }
        },
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        shape = shape,
        singleLine = singleLine,
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        contentPadding = PaddingValues(horizontal = 16.dp),
        colors = TextFieldDefaults.textFieldColors(
          containerColor = Color.Transparent,
//          textColor = TextPrimary,
          cursorColor = Primary,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent,
          disabledIndicatorColor = Color.Transparent,
        ),
      )
    })
}