package com.arekalov.compmatlab4.components

import androidx.compose.runtime.*
import com.arekalov.compmatlab4.common.StringResources
import com.arekalov.compmatlab4.components.widgets.BorderBox
import com.arekalov.compmatlab4.viewmodel.ApproximationViewModel
import com.arekalov.compmatlab4.widgets.AppButton
import com.arekalov.compmatlab4.widgets.AppColors
import com.arekalov.compmatlab4.widgets.AppSecondaryText
import com.arekalov.compmatlab4.widgets.AppText
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import org.jetbrains.compose.web.css.cssRem

@Composable
fun DataInputSection(
    modifier: Modifier = Modifier,
    viewModel: ApproximationViewModel
) {
    var xValue by remember { mutableStateOf("") }
    var yValue by remember { mutableStateOf("") }

    BorderBox(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(StringResources.DATA_INPUT_TITLE, fontSize = 1.5)

//            // Выбор типа аппроксимации
//            Column {
//                AppLabel("Тип аппроксимации:")
//                Select(
//                    value = viewModel.selectedType,
//                    onValueChanged = { viewModel.setApproximationType(it) },
//                    options = ApproximationType.values().toList(),
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//
//            // Ввод новой точки
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(0.5.cssRem)
//            ) {
//                Column(modifier = Modifier.weight(1f)) {
//                    AppLabel("X:")
//                    AppNumberField(
//                        value = xValue,
//                        onValueChanged = { xValue = it },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//                Column(modifier = Modifier.weight(1f)) {
//                    AppLabel("Y:")
//                    AppNumberField(
//                        value = yValue,
//                        onValueChanged = { yValue = it },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//                AppButton(
//                    onClick = {
//                        val x = xValue.toDoubleOrNull()
//                        val y = yValue.toDoubleOrNull()
//                        if (x != null && y != null) {
//                            viewModel.addPoint(x, y)
//                            xValue = ""
//                            yValue = ""
//                        }
//                    },
//                    modifier = Modifier.height(2.5.cssRem)
//                ) {
//                    Icon(FaPlus)
//                }
//            }
//
//            // Таблица точек
//            if (viewModel.points.isNotEmpty()) {
//                Table(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    TableHeader {
//                        TableRow {
//                            TableCell { AppText("X") }
//                            TableCell { AppText("Y") }
//                            TableCell { AppText("") }
//                        }
//                    }
//                    viewModel.points.forEachIndexed { index, point ->
//                        TableRow {
//                            TableCell { AppText(point.x.toString()) }
//                            TableCell { AppText(point.y.toString()) }
//                            TableCell {
//                                AppButton(
//                                    onClick = { viewModel.removePoint(index) },
//                                    modifier = Modifier.padding(0.25.cssRem)
//                                ) {
//                                    Icon(FaTrash)
//                                }
//                            }
//                        }
//                    }
//                }
//            }

            // Кнопки управления
            Row(
                horizontalArrangement = Arrangement.spacedBy(0.5.cssRem),
                modifier = Modifier.fillMaxWidth()
            ) {
                AppButton(
                    onClick = { viewModel.clearPoints() },
                    modifier = Modifier.weight(1f)
                ) {
                    AppText(StringResources.CLEAR_BUTTON)
                }
                AppButton(
                    onClick = { viewModel.performApproximation() },
                    modifier = Modifier.weight(1f)
                ) {
                    AppText(StringResources.COMPUTE_BUTTON)
                }
            }

            // Сообщение об ошибке
            viewModel.errorMessage?.let { error ->
                AppSecondaryText(
                    text = error,
                    color = AppColors.Error,
                    modifier = Modifier.padding(top = 0.5.cssRem)
                )
            }
        }
    }
} 