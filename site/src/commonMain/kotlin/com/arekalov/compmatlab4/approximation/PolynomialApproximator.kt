package com.arekalov.compmatlab4.approximation

import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.models.ApproximationType
import com.arekalov.compmatlab4.models.Point
import kotlin.math.pow

/**
 * Полиномиальная аппроксимация n-й степени
 * y = a0 + a1*x + a2*x^2 + ... + an*x^n
 */
class PolynomialApproximator(
    private val degree: Int
) : BaseApproximator() {
    
    init {
        require(degree in 2..3) { "Поддерживаются только полиномы 2-й и 3-й степени" }
    }

    override val type = when(degree) {
        2 -> ApproximationType.POLYNOMIAL_2
        3 -> ApproximationType.POLYNOMIAL_3
        else -> throw IllegalStateException("Неподдерживаемая степень полинома: $degree")
    }

    override fun approximate(points: List<Point>): ApproximationResult {
        validatePoints(points)
        
        // Формируем систему нормальных уравнений
        val matrix = Array(degree + 1) { DoubleArray(degree + 2) }
        
        // Заполняем матрицу коэффициентов
        for (i in 0..degree) {
            for (j in 0..degree) {
                matrix[i][j] = points.sumOf { it.x.pow(i + j) }
            }
            // Заполняем свободные члены
            matrix[i][degree + 1] = points.sumOf { it.y * it.x.pow(i) }
        }
        
        // Решаем систему методом Гаусса
        val coefficients = solveGauss(matrix)
        
        val function: (Double) -> Double = { x ->
            coefficients.mapIndexed { power, coef -> coef * x.pow(power) }.sum()
        }
        
        return ApproximationResult(
            type = type,
            coefficients = coefficients.toList(),
            meanSquareError = calculateMeanSquareError(points, function),
            pearsonCorrelation = null,
            determinationCoefficient = calculateDeterminationCoefficient(points, function),
            function = function
        )
    }
    
    private fun solveGauss(matrix: Array<DoubleArray>): DoubleArray {
        val n = matrix.size
        
        // Прямой ход
        for (k in 0 until n) {
            // Поиск максимального элемента для частичного выбора главного элемента
            var maxRow = k
            for (i in k + 1 until n) {
                if (kotlin.math.abs(matrix[i][k]) > kotlin.math.abs(matrix[maxRow][k])) {
                    maxRow = i
                }
            }
            
            // Обмен строк
            if (maxRow != k) {
                val temp = matrix[k]
                matrix[k] = matrix[maxRow]
                matrix[maxRow] = temp
            }
            
            // Нормализация уравнений
            for (i in k + 1 until n) {
                val factor = matrix[i][k] / matrix[k][k]
                for (j in k until n + 1) {
                    matrix[i][j] -= factor * matrix[k][j]
                }
            }
        }
        
        // Обратный ход
        val result = DoubleArray(n)
        for (i in n - 1 downTo 0) {
            var sum = 0.0
            for (j in i + 1 until n) {
                sum += matrix[i][j] * result[j]
            }
            result[i] = (matrix[i][n] - sum) / matrix[i][i]
        }
        
        return result
    }
} 