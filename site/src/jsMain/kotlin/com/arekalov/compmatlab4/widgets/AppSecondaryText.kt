package com.arekalov.compmatlab4.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.cssRem

@Composable
fun AppSecondaryText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Double = 0.875,
    color: Color = AppColors.Secondary
) {
    SpanText(
        text = text,
        modifier = modifier
            .color(color)
            .fontSize(fontSize.cssRem)
    )
} 