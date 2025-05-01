package com.arekalov.compmatlab4.data

import com.arekalov.compmatlab4.models.Point
import kotlinx.browser.document
import org.w3c.dom.HTMLScriptElement

private const val DESMOS_API_URI = "https://www.desmos.com/api/v1.10/calculator.js?apiKey=dcb31709b452b1cf9dc26972add0fda6"
private const val CALCULATOR_DIV_ID = "calculator"

private var calculator: dynamic = null

actual class GraphManager {
    actual fun initGraph() {
        val script = document.createElement("script") as HTMLScriptElement
        script.src = DESMOS_API_URI
        script.onload = {
            val elt = document.getElementById(CALCULATOR_DIV_ID)
            if (elt != null) {
                calculator = js(
                    """
                    new Desmos.GraphingCalculator(elt, {
                        invertedColors: true,
                        expressions: false,
                        settingsMenu: false,
                    })
                """
                )
            }
        }
        document.body?.appendChild(script)
    }

    actual fun clearGraph() {
        calculator?.setBlank()
    }

    actual fun plotPoints(points: List<Point>) {
        val value = {points.joinToString(",") { "(${it.x},${it.y})"}}
        calculator?.setExpression(js("""
            {
                id: "points",
                latex: "value",
                style: "points",
                color: "#C37C10"
            }
        """))
    }

    actual fun plotFunction(expression: String) {
        calculator?.setExpression(js("""
            {
                id: "function",
                latex: "y = expression",
                color: "#0C24A4"
            }
        """))
    }

    actual fun setTheme(isDark: Boolean) {
        calculator?.updateSettings(js("{'invertedColors': isDark}"))
    }
} 