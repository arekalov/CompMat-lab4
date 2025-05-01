package com.arekalov.compmatlab2.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arekalov.compmatlab4.components.common.PAGE_TITLE
import com.arekalov.compmatlab4.components.components.layouts.PageLayout
import com.arekalov.compmatlab2.components.sections.input.InputForm
import com.arekalov.compmatlab4.components.components.widgets.BorderBox
import com.arekalov.compmatlab4.components.components.widgets.DesmosGraph
import com.arekalov.compmatlab2.data.common.SingleEquation
import com.arekalov.compmatlab4.components.data.initGraph
import com.arekalov.compmatlab2.ui.SingleAction
import com.arekalov.compmatlab2.ui.MainViewModel
import com.arekalov.compmatlab2.ui.SystemAction
import com.arekalov.compmatlab2.ui.model.Method
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun Index() {
    val viewModel by remember { mutableStateOf(MainViewModel()) }
    val singleState = viewModel.singleState.collectAsState().value
    val systemState = viewModel.systemState.collectAsState().value

    val onAChanged = remember { { value: Double? -> viewModel.reduce(SingleAction.ChangeA(value)) } }
    val onBChanged = remember { { value: Double? -> viewModel.reduce(SingleAction.ChangeB(value)) } }
    val onEquationChanged = remember { { value: SingleEquation -> viewModel.reduce(SingleAction.ChangeEquation(value)) } }
    val onSingleMethodChanged = remember { { method: String -> viewModel.reduce(SingleAction.ChangeMethod(method)) } }

    val onXChanged = remember { { value: Double? -> viewModel.reduce(SystemAction.ChangeX(value)) } }
    val onYChanged = remember { { value: Double? -> viewModel.reduce(SystemAction.ChangeY(value)) } }
    val onFirstEquationChanged =
        remember { { value: SingleEquation -> viewModel.reduce(SystemAction.ChangeFirstEquation(value)) } }
    val onSecondEquationChanged =
        remember { { value: SingleEquation -> viewModel.reduce(SystemAction.ChangeSecondEquation(value)) } }
    val onSystemMethodChanged =
        remember { { method: Method -> viewModel.reduce(SystemAction.ChangeMethod(method)) } }

    val onSolvedSystemClicked = remember { { viewModel.reduce(SystemAction.Calculate) } }
    val onSolvedSingleClicked = remember { { viewModel.reduce(SingleAction.Calculate) } }
    var isSingleMode by remember { mutableStateOf(true) }

    PageLayout(
        title = PAGE_TITLE
    ) {
        LaunchedEffect(Unit) {
            initGraph()
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.5.cssRem)
        ) {
            BorderBox {
                InputForm(
                    singleState = singleState,
                    systemState = systemState,
                    onAChanged = onAChanged,
                    onBChanged = onBChanged,
                    onSolvedClicked = if (isSingleMode) {
                        onSolvedSingleClicked
                    } else {
                        onSolvedSystemClicked
                    },
                    onEquationChanged = onEquationChanged,
                    onFirstEquationChanged = onFirstEquationChanged,
                    onSecondEquationChanged = onSecondEquationChanged,
                    onXChanged = onXChanged,
                    onYChanged = onYChanged,
                    isSingleMode = isSingleMode,
                    onSingleModeChanged = { value -> isSingleMode = value },
                    onSingleEqMethodChanged = onSingleMethodChanged,
                    onSystemMethodChanged = onSystemMethodChanged,
                )
            }
            DesmosGraph(
                width = 45f,
                height = 40f,
            )
            BorderBox(
                modifier = Modifier
                    .width(30.cssRem)
                    .height(23.5.cssRem)
            ) {
                Text(
                    if (isSingleMode) {
                        singleState.solution.toString()
                    } else {
                        systemState.solution.toString()
                    }
                )
            }
        }
    }
}
