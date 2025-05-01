package com.arekalov.compmatlab4.approximation

import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.models.ApproximationType
import com.arekalov.compmatlab4.models.Point

/**
 * Линейная аппроксимация вида y = ax + b
 */
class LinearApproximator : BaseApproximator() {
    override val type = ApproximationType.LINEAR

    override fun approximate(points: List<Point>): ApproximationResult {
        validatePoints(points)
        
        val n = points.size
        val sumX = points.sumOf { it.x }
        val sumY = points.sumOf { it.y }
        val sumXY = points.sumOf { it.x * it.y }
        val sumX2 = points.sumOf { it.x * it.x }
        
        // Решаем систему уравнений методом Крамера:
        // a * sumX2 + b * sumX = sumXY
        // a * sumX + b * n = sumY
        val determinant = sumX2 * n - sumX * sumX
        val a = (sumXY * n - sumX * sumY) / determinant
        val b = (sumX2 * sumY - sumX * sumXY) / determinant
        
        val function: (Double) -> Double = { x -> a * x + b }
        
        return ApproximationResult(
            type = type,
            coefficients = listOf(a, b),
            meanSquareError = calculateMeanSquareError(points, function),
            pearsonCorrelation = calculatePearsonCorrelation(points),
            determinationCoefficient = calculateDeterminationCoefficient(points, function),
            function = function
        )
    }
}
