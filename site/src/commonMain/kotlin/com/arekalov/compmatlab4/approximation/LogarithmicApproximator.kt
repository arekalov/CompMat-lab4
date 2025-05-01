package com.arekalov.compmatlab4.approximation

import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.models.ApproximationType
import com.arekalov.compmatlab4.models.Point
import kotlin.math.ln

/**
 * Логарифмическая аппроксимация вида y = a*ln(x) + b
 */
class LogarithmicApproximator : BaseApproximator() {
    override val type = ApproximationType.LOGARITHMIC

    override fun approximate(points: List<Point>): ApproximationResult {
        validatePoints(points)
        
        // Преобразуем x-координаты для линеаризации
        val transformedPoints = points.map { Point(ln(it.x), it.y) }
        
        val n = points.size
        val sumLnX = transformedPoints.sumOf { it.x }
        val sumY = transformedPoints.sumOf { it.y }
        val sumLnXY = transformedPoints.sumOf { it.x * it.y }
        val sumLnX2 = transformedPoints.sumOf { it.x * it.x }
        
        // Решаем систему уравнений
        val determinant = sumLnX2 * n - sumLnX * sumLnX
        val a = (sumLnXY * n - sumLnX * sumY) / determinant
        val b = (sumLnX2 * sumY - sumLnX * sumLnXY) / determinant
        
        val function: (Double) -> Double = { x -> a * ln(x) + b }
        
        return ApproximationResult(
            type = type,
            coefficients = listOf(a, b),
            meanSquareError = calculateMeanSquareError(points, function),
            pearsonCorrelation = null,
            determinationCoefficient = calculateDeterminationCoefficient(points, function),
            function = function
        )
    }
} 