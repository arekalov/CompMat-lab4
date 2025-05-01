package com.arekalov.compmatlab4.models

enum class ApproximationType(val displayName: String) {
    LINEAR("Линейная функция"),
    POLYNOMIAL_2("Полиномиальная 2-й степени"),
    POLYNOMIAL_3("Полиномиальная 3-й степени"),
    EXPONENTIAL("Экспоненциальная функция"),
    LOGARITHMIC("Логарифмическая функция"),
    POWER("Степенная функция");

    override fun toString(): String = displayName
}
