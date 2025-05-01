package com.arekalov.compmatlab4.approximation

import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.models.ApproximationType
import com.arekalov.compmatlab4.models.Point
import kotlin.math.exp
import kotlin.math.ln

/**
 * Экспоненциальная аппроксимация вида y = a*e^(bx)
 * Линеаризуется к виду ln(y) = ln(a) + bx
 */
class ExponentialApproximator : BaseApproximator() {
    override val type = ApproximationType.EXPONENTIAL

    override fun approximate(points: List<Point>): ApproximationResult {
        validatePoints(points)
        
        // Преобразуем точки для линеаризации
        val transformedPoints = points.map { Point(it.x, ln(it.y)) }
        
        val n = points.size
        val sumX = transformedPoints.sumOf { it.x }
        val sumY = transformedPoints.sumOf { it.y }
        val sumXY = transformedPoints.sumOf { it.x * it.y }
        val sumX2 = transformedPoints.sumOf { it.x * it.x }
        
        // Решаем систему для линеаризованной функции
        val determinant = sumX2 * n - sumX * sumX
        val b = (sumXY * n - sumX * sumY) / determinant
        val lnA = (sumX2 * sumY - sumX * sumXY) / determinant
        val a = exp(lnA)
        
        val function: (Double) -> Double = { x -> a * exp(b * x) }
        
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