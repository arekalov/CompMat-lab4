package com.arekalov.compmatlab4.components

import androidx.compose.runtime.*
import com.arekalov.compmatlab4.common.StringResources
import com.arekalov.compmatlab4.components.widgets.BorderBox
import com.arekalov.compmatlab4.models.Point
import com.arekalov.compmatlab4.viewmodel.ApproximationViewModel
import com.arekalov.compmatlab4.widgets.AppButton
import com.arekalov.compmatlab4.widgets.AppColors
import com.arekalov.compmatlab4.widgets.AppSecondaryText
import com.arekalov.compmatlab4.widgets.AppText
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.TextArea

@Composable
fun DataInputSection(
    modifier: Modifier = Modifier,
    viewModel: ApproximationViewModel
) {
    var inputText by remember { mutableStateOf("") }

    BorderBox(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(1.cssRem)
        ) {
            // Левая часть - поле ввода
            Column(
                verticalArrangement = Arrangement.spacedBy(1.cssRem),
                modifier = Modifier.weight(1f)
            ) {
                AppText(StringResources.DATA_INPUT_TITLE, fontSize = 1.5)

                TextArea(
                    value = inputText,
                    attrs = Modifier
                        .fillMaxWidth()
                        .height(10.cssRem)
                        .padding(0.5.cssRem)
                        .toAttrs {
                            onInput { event ->
                                inputText = event.value
                            }
                        }
                )

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
                        onClick = {
                            try {
                                val points = inputText.trim()
                                    .split("\n")
                                    .filter { it.isNotBlank() }
                                    .map { line ->
                                        val (x, y) = line.trim().split("\\s+".toRegex())
                                        Point(x.toDouble(), y.toDouble())
                                    }
                                viewModel.clearPoints()
                                points.forEach { point ->
                                    viewModel.addPoint(point.x, point.y)
                                }
                                viewModel.performApproximation()
                            } catch (e: Exception) {
                                viewModel.errorMessage = "Ошибка при парсинге данных: ${e.message}"
                            }
                        },
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

            // Правая часть - подсказка о формате
            Column(
                modifier = Modifier
                    .width(20.cssRem)
                    .padding(1.cssRem)
            ) {
                AppText("Формат ввода данных:", fontSize = 1.2)
                Column(
                    modifier = Modifier
                        .padding(top = 0.5.cssRem)
                ) {
                    AppSecondaryText("Введите координаты точек в формате:")
                    AppSecondaryText("x y")
                    AppSecondaryText("x y")
                    AppSecondaryText("x y")
                    AppSecondaryText("")
                    AppSecondaryText("Например:")
                    AppSecondaryText("1.0 2.5")
                    AppSecondaryText("2.0 3.7")
                    AppSecondaryText("3.0 4.2")
                    AppSecondaryText("")
                    AppSecondaryText("Минимум 8 точек")
                    AppSecondaryText("Максимум 12 точек")
                }
            }
        }
    }
}