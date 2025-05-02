package com.arekalov.compmatlab4.pages

import androidx.compose.runtime.Composable
import com.arekalov.compmatlab4.common.PAGE_TITLE
import com.arekalov.compmatlab4.components.DataInputSection
import com.arekalov.compmatlab4.components.GraphSection
import com.arekalov.compmatlab4.components.ResultSection
import com.arekalov.compmatlab4.components.layouts.PageLayout
import com.arekalov.compmatlab4.viewmodel.ApproximationViewModel
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.*

@Page
@Composable
fun Index() {
    val viewModel = ApproximationViewModel()
    PageLayout(
        title = PAGE_TITLE,
        onThemeChanged = viewModel::setTheme,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(1.cssRem),
            horizontalArrangement = Arrangement.spacedBy(1.cssRem)
        ) {
            // Левая колонка с вводом данных и результатами
            Column(
                modifier = Modifier
                    .width(40.cssRem)
                    .fillMaxHeight()
                    .overflow(Overflow.Auto),
                verticalArrangement = Arrangement.spacedBy(1.cssRem)
            ) {
                DataInputSection(viewModel = viewModel)
                ResultSection(viewModel = viewModel)
            }

            // Правая колонка с графиком
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.cssRem)
            ) {
                GraphSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                )
            }
        }
    }
}
