package com.arekalov.lab4.models

import kotlin.math.pow
import kotlin.math.sqrt

abstract class BaseApproximator : Approximator {
    
    protected fun validatePoints(points: List<Point>) {
        require(points.size in Approximator.MIN_POINTS..Approximator.MAX_POINTS) {
            "Количество точек должно быть от ${Approximator.MIN_POINTS} до ${Approximator.MAX_POINTS}, получено: ${points.size}"
        }
    }

    protected fun calculateMeanSquareError(points: List<Point>, function: (Double) -> Double): Double {
        val sumSquaredErrors = points.sumOf { (x, y) ->
            val predicted = function(x)
            (predicted - y).pow(2)
        }
        return sqrt(sumSquaredErrors / points.size)
    }

    protected fun calculateDeterminationCoefficient(points: List<Point>, function: (Double) -> Double): Double {
        val meanY = points.map { it.y }.average()
        val totalSum = points.sumOf { (it.y - meanY).pow(2) }
        val residualSum = points.sumOf { point -> (point.y - function(point.x)).pow(2) }
        return 1 - (residualSum / totalSum)
    }

    protected fun calculatePearsonCorrelation(points: List<Point>): Double {
        val meanX = points.map { it.x }.average()
        val meanY = points.map { it.y }.average()
        
        val numerator = points.sumOf { (x, y) -> (x - meanX) * (y - meanY) }
        val denominatorX = sqrt(points.sumOf { (x, _) -> (x - meanX).pow(2) })
        val denominatorY = sqrt(points.sumOf { (_, y) -> (y - meanY).pow(2) })
        
        return numerator / (denominatorX * denominatorY)
    }
} 