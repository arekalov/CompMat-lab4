package com.arekalov.compmatlab4.models

enum class ApproximationType(val color: String, val colorInt: Int, val invertedColor: String) {
    LINEAR("#FF0000", 0xFF0000, "#00FFFF"), // Красный -> Голубой
    POLYNOMIAL_2("#00FF00", 0x00FF00, "#FF00FF"), // Зеленый -> Магента
    POLYNOMIAL_3("#0000FF", 0x0000FF, "#FFFF00"), // Синий -> Желтый
    EXPONENTIAL("#FFA500", 0xFFA500, "#005AFF"), // Оранжевый -> Голубовато-синий
    LOGARITHMIC("#800080", 0x800080, "#7FFF7F"), // Фиолетовый -> Бледно-зеленый
    POWER("#008080", 0x008080, "#FF7F7F"); // Бирюзовый -> Светло-красный

    override fun toString(): String {
        return when (this) {
            LINEAR -> "Линейная"
            POLYNOMIAL_2 -> "Полиномиальная 2-й степени"
            POLYNOMIAL_3 -> "Полиномиальная 3-й степени"
            EXPONENTIAL -> "Экспоненциальная"
            LOGARITHMIC -> "Логарифмическая"
            POWER -> "Степенная"
        }
    }

}
