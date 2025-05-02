package com.arekalov.compmatlab4.models

data class ApproximationResult(
    val type: ApproximationType,
    val coefficients: List<Double>,
    val meanSquareError: Double,
    val deviation: Double,
    val pearsonCorrelation: Double? = null, // Только для линейной аппроксимации
    val determinationCoefficient: Double,
    val function: (Double) -> Double
) {
    val isGoodFit: Boolean
        get() = determinationCoefficient > 0.7

    fun evaluate(x: Double): Double = function(x)

    override fun toString(): String {
        val coefficientsStr = coefficients.joinToString(", ")
        return """
            Тип аппроксимации: $type
            Коэффициенты: [$coefficientsStr]
            Среднеквадратичное отклонение: $meanSquareError
            Мера отклонения: $deviation
            ${pearsonCorrelation?.let { "Коэффициент корреляции Пирсона: $it" } ?: ""}
            Коэффициент детерминации: $determinationCoefficient
            Качество аппроксимации: ${if (isGoodFit) "хорошее" else "недостаточное"}
        """.trimIndent()
    }
}
