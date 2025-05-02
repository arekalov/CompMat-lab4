package com.arekalov.compmatlab4.data

import com.arekalov.compmatlab4.models.Point

expect class GraphManager() {
    fun initGraph()
    fun clearGraph()
    fun plotPoints(points: List<Point>)
    fun plotFunction(expression: String, color: String = "#0C24A4")
    fun setTheme(isDark: Boolean)
    fun jsLog(value: String)
} 