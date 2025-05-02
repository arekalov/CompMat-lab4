package com.arekalov.compmatlab4.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arekalov.compmatlab4.approximation.*
import com.arekalov.compmatlab4.data.GraphManager
import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.models.ApproximationType
import com.arekalov.compmatlab4.models.Point
import kotlin.math.pow
import kotlin.math.round

class ApproximationViewModel {
    private val graphManager = GraphManager()

    // Состояние точек данных
    var points by mutableStateOf<List<Point>>(emptyList())
        private set

    // Результаты всех типов аппроксимации
    var allResults by mutableStateOf<Map<ApproximationType, ApproximationResult>>(emptyMap())
        private set

    // Наилучший результат
    var bestResult by mutableStateOf<ApproximationResult?>(null)
        private set

    // Сообщение об ошибке
    var errorMessage by mutableStateOf<String?>(null)
        internal set

    var isDarkTheme by mutableStateOf(true)

    // Фабрика аппроксиматоров
    private val approximatorFactory = mapOf(
        ApproximationType.LINEAR to { LinearApproximator() },
        ApproximationType.POLYNOMIAL_2 to { PolynomialApproximator(2) },
        ApproximationType.POLYNOMIAL_3 to { PolynomialApproximator(3) },
        ApproximationType.EXPONENTIAL to { ExponentialApproximator() },
        ApproximationType.LOGARITHMIC to { LogarithmicApproximator() },
        ApproximationType.POWER to { PowerApproximator() }
    )

    init {
        graphManager.initGraph()
    }

    fun setTheme(isDark: Boolean) {
        isDarkTheme = isDark
        graphManager.setTheme(isDark)
    }

    // Добавление новой точки
    fun addPoint(x: Double, y: Double) {
        if (points.size >= Approximator.MAX_POINTS) {
            errorMessage = "Достигнуто максимальное количество точек (${Approximator.MAX_POINTS})"
            return
        }
        points = points + Point(x, y)
        errorMessage = null
        updateGraph()
    }

    // Удаление точки по индексу
    fun removePoint(index: Int) {
        if (index in points.indices) {
            points = points.filterIndexed { i, _ -> i != index }
            errorMessage = null
            updateGraph()
        }
    }

    // Очистка всех точек
    fun clearPoints() {
        points = emptyList()
        allResults = emptyMap()
        bestResult = null
        errorMessage = null
        graphManager.clearGraph()
    }

    // Выполнение аппроксимации
    fun performApproximation() {
        try {
            if (points.size < Approximator.MIN_POINTS) {
                errorMessage = "Необходимо ввести минимум ${Approximator.MIN_POINTS} точек"
                return
            }

            // Вычисляем все типы аппроксимации
            val results = mutableMapOf<ApproximationType, ApproximationResult>()
            approximatorFactory.forEach { (type, factory) ->
                try {
                    val approximator = factory()
                    results[type] = approximator.approximate(points)
                } catch (e: Exception) {
                    // Пропускаем типы аппроксимации, которые не подходят для данных
                }
            }

            allResults = results

            // Находим наилучшую аппроксимацию по коэффициенту детерминации
            bestResult = results.maxByOrNull { it.value.determinationCoefficient }?.value

            errorMessage = null
            updateGraph()
        } catch (e: Exception) {
            errorMessage = e.message ?: "Произошла ошибка при выполнении аппроксимации"
            allResults = emptyMap()
            bestResult = null
            updateGraph()
        }
    }

    // Обновление графика
    private fun updateGraph() {
        graphManager.clearGraph()
        graphManager.plotPoints(points)
        
        // Отображаем все функции
        allResults.forEach { (type, result) ->
            graphManager.plotFunction(
                expression = getFunctionExpression(result),
                color = if (isDarkTheme) type.invertedColor else type.color
            )
        }
    }

    // Получение выражения функции для графика
    private fun getFunctionExpression(result: ApproximationResult): String {
        return when (result.type) {
            ApproximationType.LINEAR -> {
                val (a, b) = result.coefficients
                "${formatNumber(a)}x + ${formatNumber(b)}"
            }
            ApproximationType.POLYNOMIAL_2 -> {
                val (a0, a1, a2) = result.coefficients
                "${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x^2"
            }
            ApproximationType.POLYNOMIAL_3 -> {
                val (a0, a1, a2, a3) = result.coefficients
                "${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x^2 + ${formatNumber(a3)}x^3"
            }
            ApproximationType.EXPONENTIAL -> {
                val (a, b) = result.coefficients
                "${formatNumber(a)}e^{${formatNumber(b)}x}"
            }
            ApproximationType.LOGARITHMIC -> {
                val (a, b) = result.coefficients
                "${formatNumber(a)}\\ln(x) + ${formatNumber(b)}"
            }
            ApproximationType.POWER -> {
                val (a, b) = result.coefficients
                "${formatNumber(a)}x^{${formatNumber(b)}}"
            }
        }
    }

    // Получение функции для построения графика
    fun getFunction(): ((Double) -> Double)? = bestResult?.function

    // Получение строкового представления функции
    fun getFunctionString(result: ApproximationResult): String {
        return when (result.type) {
            ApproximationType.LINEAR -> {
                val (a, b) = result.coefficients
                "y = ${formatNumber(a)}x + ${formatNumber(b)}"
            }
            ApproximationType.POLYNOMIAL_2 -> {
                val (a0, a1, a2) = result.coefficients
                "y = ${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x²"
            }
            ApproximationType.POLYNOMIAL_3 -> {
                val (a0, a1, a2, a3) = result.coefficients
                "y = ${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x² + ${formatNumber(a3)}x³"
            }
            ApproximationType.EXPONENTIAL -> {
                val (a, b) = result.coefficients
                "y = ${formatNumber(a)}e^(${formatNumber(b)}x)"
            }
            ApproximationType.LOGARITHMIC -> {
                val (a, b) = result.coefficients
                "y = ${formatNumber(a)}ln(x) + ${formatNumber(b)}"
            }
            ApproximationType.POWER -> {
                val (a, b) = result.coefficients
                "y = ${formatNumber(a)}x^${formatNumber(b)}"
            }
        }
    }

    private fun formatNumber(number: Double): String {
        return if (kotlin.math.abs(number) < 1e-10) "0" else formatNumber(number, 4)
    }

    private fun formatNumber(number: Double, precision: Int): String {
        val factor = 10.0.pow(precision)
        val roundedNumber = round(number * factor) / factor
        return roundedNumber.toString()
    }
} 