package com.arekalov.compmatlab4.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import org.jetbrains.compose.web.css.cssRem

@Composable
fun EditTextWithLabel(
    text: String = "",
    onInput: (String) -> Unit,
    hint: String = "",
    labelText: String = "",
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 1.cssRem),
        modifier = modifier,
    ) {
        if (labelText.isNotBlank()) {
            RegularText(text = labelText)
        }
        EditText(
            text = text,
            onInput = onInput,
            hint = hint,
        )
    }
}