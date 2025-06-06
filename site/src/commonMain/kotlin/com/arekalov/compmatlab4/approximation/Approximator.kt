package com.arekalov.compmatlab4.approximation

import com.arekalov.compmatlab4.models.ApproximationResult
import com.arekalov.compmatlab4.models.ApproximationType
import com.arekalov.compmatlab4.models.Point

interface Approximator {
    val type: ApproximationType
    
    /**
     * Выполняет аппроксимацию по заданным точкам
     * @param points список точек для аппроксимации (должен содержать от 8 до 12 точек)
     * @return результат аппроксимации
     * @throws IllegalArgumentException если количество точек не соответствует требованиям
     */
    fun approximate(points: List<Point>): ApproximationResult

    companion object {
        const val MIN_POINTS = 8
        const val MAX_POINTS = 12
    }
}
