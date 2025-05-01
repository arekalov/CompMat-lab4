package com.arekalov.compmatlab4.components

import androidx.compose.runtime.Composable
import com.arekalov.compmatlab4.components.widgets.BorderBox
import com.arekalov.compmatlab4.viewmodel.ApproximationViewModel
import com.arekalov.compmatlab4.widgets.AppColors
import com.arekalov.compmatlab4.widgets.AppSecondaryText
import com.arekalov.compmatlab4.widgets.AppText
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import org.jetbrains.compose.web.css.*
import kotlin.math.pow
import kotlin.math.round

@Composable
fun ResultSection(
    modifier: Modifier = Modifier,
    viewModel: ApproximationViewModel
) {
    BorderBox(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText("Результаты", fontSize = 1.5)

            viewModel.result?.let { result ->
                // Функция
                ResultItem("Функция", viewModel.getFunctionString())

                // Коэффициенты
                ResultItem(
                    "Коэффициенты",
                    result.coefficients.joinToString(", ") { formatNumber(it) }
                )

                // Статистика
                ResultItem(
                    "Среднеквадратичное отклонение",
                    formatNumber(result.meanSquareError)
                )

                result.pearsonCorrelation?.let { correlation ->
                    ResultItem(
                        "Коэффициент корреляции Пирсона",
                        formatNumber(correlation)
                    )
                }

                ResultItem(
                    "Коэффициент детерминации",
                    formatNumber(result.determinationCoefficient)
                )

                ResultItem(
                    "Качество аппроксимации",
                    if (result.isGoodFit) "Хорошее" else "Недостаточное"
                )
            } ?: run {
                AppSecondaryText(
                    "Введите данные и нажмите 'Вычислить'",
                    modifier = Modifier.padding(1.cssRem)
                )
            }
        }
    }
}

@Composable
private fun ResultItem(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(AppColors.Surface)
            .padding(0.75.cssRem)
            .borderRadius(0.5.cssRem)
    ) {
        AppSecondaryText(label)
        AppText(value)
    }
}

private fun formatNumber(number: Double, precision: Int = 4): String {
    val factor = 10.0.pow(precision)
    val roundedNumber = round(number * factor) / factor
    return roundedNumber.toString()
}
