package com.arekalov.compmatlab4.components

import androidx.compose.runtime.Composable
import com.arekalov.compmatlab4.components.widgets.BorderBox
import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.viewmodel.ApproximationViewModel
import com.arekalov.compmatlab4.widgets.AppColors
import com.arekalov.compmatlab4.widgets.AppSecondaryText
import com.arekalov.compmatlab4.widgets.AppText
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.graphics.Color as kobwebColor
import org.jetbrains.compose.web.css.*
import kotlin.math.pow
import kotlin.math.round
import com.arekalov.compmatlab4.common.StringResources

@Composable
fun ResultSection(
    modifier: Modifier = Modifier,
    viewModel: ApproximationViewModel
) {
    BorderBox(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
            modifier = modifier.fillMaxWidth()
        ) {
            AppText(StringResources.RESULT_TITLE, fontSize = 1.5)

            if (viewModel.allResults.isNotEmpty()) {
                // Выводим результаты для каждого типа аппроксимации
                viewModel.allResults.forEach { (type, result) ->
                    ResultItem(
                        title = type.toString(),
                        result = result,
                        isBest = result == viewModel.bestResult,
                        viewModel = viewModel
                    )
                }

                // Выводим итоговый результат
                viewModel.bestResult?.let { bestResult ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .backgroundColor(AppColors.Surface)
                            .padding(1.cssRem)
                            .borderRadius(0.5.cssRem)
                    ) {
                        AppText(
                            "${StringResources.BEST_APPROX_LABEL} ${bestResult.type}",
                            fontSize = 1.2,
                            color = AppColors.Success
                        )
                        AppSecondaryText(
                            "${StringResources.DEVIATION_LABEL} ${formatNumber(bestResult.deviation)}",
                            modifier = Modifier.padding(top = 0.5.cssRem)
                        )
                    }
                }
            } else {
                AppSecondaryText(
                    StringResources.NO_RESULTS_MESSAGE,
                    modifier = Modifier.padding(1.cssRem)
                )
            }
        }
    }
}

@Composable
private fun ResultItem(
    title: String,
    result: ApproximationResult,
    isBest: Boolean,
    viewModel: ApproximationViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(if (isBest) AppColors.Surface else Color.transparent)
            .padding(0.75.cssRem)
            .borderRadius(0.5.cssRem)
    ) {
        AppText(
            title,
            fontSize = 1.1,
            color = kobwebColor.rgb(result.type.colorInt)
        )
        
        AppSecondaryText(
            viewModel.getFunctionString(result),
            modifier = Modifier.padding(top = 0.25.cssRem)
        )

        AppSecondaryText(
            "${StringResources.COEFFICIENTS_LABEL} ${result.coefficients.joinToString(", ") { formatNumber(it) }}",
            modifier = Modifier.padding(top = 0.25.cssRem)
        )

        AppSecondaryText(
            "${StringResources.MEAN_SQUARE_ERROR_LABEL} ${formatNumber(result.meanSquareError)}",
            modifier = Modifier.padding(top = 0.25.cssRem)
        )

        AppSecondaryText(
            "${StringResources.DEVIATION_LABEL} ${formatNumber(result.deviation)}",
            modifier = Modifier.padding(top = 0.25.cssRem)
        )

        result.pearsonCorrelation?.let { correlation ->
            AppSecondaryText(
                "${StringResources.PEARSON_LABEL} ${formatNumber(correlation)}",
                modifier = Modifier.padding(top = 0.25.cssRem)
            )
        }

        AppSecondaryText(
            "${StringResources.DETERMINATION_LABEL} ${formatNumber(result.determinationCoefficient)}",
            modifier = Modifier.padding(top = 0.25.cssRem)
        )

        AppSecondaryText(
            "${StringResources.QUALITY_LABEL} ${result.approximationQuality}",
            modifier = Modifier.padding(top = 0.25.cssRem)
        )
    }
}

private fun formatNumber(number: Double, precision: Int = 4): String {
    val factor = 10.0.pow(precision)
    val roundedNumber = round(number * factor) / factor
    return roundedNumber.toString()
}
