package com.arekalov.compmatlab4.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.theme.colors.ColorPalettes

@Composable
fun Toggle(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colorPalette = ColorPalettes.Monochrome,
    )
}
