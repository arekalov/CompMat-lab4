package com.arekalov.compmatlab4.pages

import androidx.compose.runtime.*
import com.arekalov.compmatlab4.common.PAGE_TITLE
import com.arekalov.compmatlab4.components.*
import com.arekalov.compmatlab4.components.layouts.PageLayout
import com.arekalov.compmatlab4.viewmodel.ApproximationViewModel
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.*

@Page
@Composable
fun Index() {
    PageLayout(title = PAGE_TITLE) {
        val viewModel = remember { ApproximationViewModel() }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.cssRem),
            verticalArrangement = Arrangement.spacedBy(1.cssRem)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.cssRem)
            ) {
                // Левая секция - ввод данных
                DataInputSection(
                    modifier = Modifier
                        .width(30.cssRem)
                        .fillMaxHeight(),
                    viewModel = viewModel
                )

                // Центральная секция - график
                GraphSection(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

                // Правая секция - результаты
                ResultSection(
                    modifier = Modifier
                        .width(30.cssRem)
                        .fillMaxHeight(),
                    viewModel = viewModel
                )
            }
        }
    }
}
