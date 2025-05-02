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

    // Выбранный тип аппроксимации
    var selectedType by mutableStateOf<ApproximationType>(ApproximationType.LINEAR)
        private set

    // Результат аппроксимации
    var result by mutableStateOf<ApproximationResult?>(null)
        private set

    // Сообщение об ошибке
    var errorMessage by mutableStateOf<String?>(null)
        internal set

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
        result = null
        errorMessage = null
        graphManager.clearGraph()
    }

    // Изменение типа аппроксимации
    fun setApproximationType(type: ApproximationType) {
        selectedType = type
        errorMessage = null
        updateGraph()
    }

    // Выполнение аппроксимации
    fun performApproximation() {
        try {
            if (points.size < Approximator.MIN_POINTS) {
                errorMessage = "Необходимо ввести минимум ${Approximator.MIN_POINTS} точек"
                return
            }

            val approximator = approximatorFactory[selectedType]?.invoke()
                ?: throw IllegalStateException("Неподдерживаемый тип аппроксимации: $selectedType")

            result = approximator.approximate(points)
            errorMessage = null
            updateGraph()
        } catch (e: Exception) {
            errorMessage = e.message ?: "Произошла ошибка при выполнении аппроксимации"
            result = null
            updateGraph()
        }
    }

    // Обновление графика
    private fun updateGraph() {
        graphManager.clearGraph()
        graphManager.plotPoints(points)
        result?.let {
            graphManager.plotFunction(getFunctionExpression())
        }
    }

    // Получение выражения функции для графика
    private fun getFunctionExpression(): String {
        return when (selectedType) {
            ApproximationType.LINEAR -> {
                val (a, b) = result?.coefficients ?: return ""
                "${formatNumber(a)}x + ${formatNumber(b)}"
            }
            ApproximationType.POLYNOMIAL_2 -> {
                val (a0, a1, a2) = result?.coefficients ?: return ""
                "${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x^2"
            }
            ApproximationType.POLYNOMIAL_3 -> {
                val (a0, a1, a2, a3) = result?.coefficients ?: return ""
                "${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x^2 + ${formatNumber(a3)}x^3"
            }
            ApproximationType.EXPONENTIAL -> {
                val (a, b) = result?.coefficients ?: return ""
                "${formatNumber(a)}e^{${formatNumber(b)}x}"
            }
            ApproximationType.LOGARITHMIC -> {
                val (a, b) = result?.coefficients ?: return ""
                "${formatNumber(a)}\\ln(x) + ${formatNumber(b)}"
            }
            ApproximationType.POWER -> {
                val (a, b) = result?.coefficients ?: return ""
                "${formatNumber(a)}x^{${formatNumber(b)}}"
            }
        }
    }

    // Получение функции для построения графика
    fun getFunction(): ((Double) -> Double)? = result?.function

    // Получение строкового представления функции
    fun getFunctionString(): String {
        return when (selectedType) {
            ApproximationType.LINEAR -> {
                val (a, b) = result?.coefficients ?: return ""
                "y = ${formatNumber(a)}x + ${formatNumber(b)}"
            }
            ApproximationType.POLYNOMIAL_2 -> {
                val (a0, a1, a2) = result?.coefficients ?: return ""
                "y = ${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x²"
            }
            ApproximationType.POLYNOMIAL_3 -> {
                val (a0, a1, a2, a3) = result?.coefficients ?: return ""
                "y = ${formatNumber(a0)} + ${formatNumber(a1)}x + ${formatNumber(a2)}x² + ${formatNumber(a3)}x³"
            }
            ApproximationType.EXPONENTIAL -> {
                val (a, b) = result?.coefficients ?: return ""
                "y = ${formatNumber(a)}e^(${formatNumber(b)}x)"
            }
            ApproximationType.LOGARITHMIC -> {
                val (a, b) = result?.coefficients ?: return ""
                "y = ${formatNumber(a)}ln(x) + ${formatNumber(b)}"
            }
            ApproximationType.POWER -> {
                val (a, b) = result?.coefficients ?: return ""
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