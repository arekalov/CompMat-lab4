package com.arekalov.compmatlab4.approximation

import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.models.ApproximationType
import com.arekalov.compmatlab4.models.Point
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow

/**
 * Степенная аппроксимация вида y = ax^b
 * Линеаризуется к виду ln(y) = ln(a) + b*ln(x)
 */
class PowerApproximator : BaseApproximator() {
    override val type = ApproximationType.POWER

    override fun approximate(points: List<Point>): ApproximationResult {
        validatePoints(points)
        
        // Преобразуем точки для линеаризации
        val transformedPoints = points.map { Point(ln(it.x), ln(it.y)) }
        
        val n = points.size
        val sumLnX = transformedPoints.sumOf { it.x }
        val sumLnY = transformedPoints.sumOf { it.y }
        val sumLnXLnY = transformedPoints.sumOf { it.x * it.y }
        val sumLnX2 = transformedPoints.sumOf { it.x * it.x }
        
        // Решаем систему для линеаризованной функции
        val determinant = sumLnX2 * n - sumLnX * sumLnX
        val b = (sumLnXLnY * n - sumLnX * sumLnY) / determinant
        val lnA = (sumLnX2 * sumLnY - sumLnX * sumLnXLnY) / determinant
        val a = exp(lnA)
        
        val function: (Double) -> Double = { x -> a * x.pow(b) }
        
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