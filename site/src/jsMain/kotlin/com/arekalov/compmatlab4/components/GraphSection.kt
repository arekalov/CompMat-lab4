package com.arekalov.compmatlab4.components

import androidx.compose.runtime.Composable
import com.arekalov.compmatlab4.components.widgets.BorderBox
import com.arekalov.compmatlab4.widgets.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Composable
fun GraphSection(
    modifier: Modifier = Modifier
) {
    BorderBox(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
            modifier = Modifier.fillMaxSize()
        ) {
            AppText("График", fontSize = 1.5)
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Div(
                    attrs = {
                        id("calculator")
                        style {
                            width(100.percent)
                            height(100.percent)
                        }
                    }
                )
            }
        }
    }
} 