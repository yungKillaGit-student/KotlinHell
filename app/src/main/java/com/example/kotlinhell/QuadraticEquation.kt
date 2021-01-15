package com.example.kotlinhell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlin.math.pow
import kotlin.math.sqrt

class QuadraticEquation : AppCompatActivity() {
    fun solveEquation(a: Double, b: Double, c: Double): Array<Double> {
        val discriminant = b.pow(2.0) - 4 * a * c;

        var rootsNumber = 0;
        when {
            discriminant < 0 -> return emptyArray()
            discriminant == 0.0 -> return arrayOf((-b + sqrt(discriminant)) / (2 * a))
            discriminant > 0 -> return arrayOf((-b + sqrt(discriminant)) / 2 * a, (-b - sqrt(discriminant)) / (2 * a))
        }

        return emptyArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadratic_equation)

        bindSolveEquationEvent()
    }

    fun bindSolveEquationEvent() {
        val clickListener = View.OnClickListener { view ->
            val editFactorA = findViewById<EditText>(R.id.editFactorA)
            val factorA = editFactorA.text.toString()

            val editFactorB = findViewById<EditText>(R.id.editFactorB)
            val factorB = editFactorB.text.toString()

            val editFactorC = findViewById<EditText>(R.id.editFactorC)
            val factorC = editFactorC.text.toString()

            fun formatNumber(number: Double): Number {
                if (number % 1 == 0.0) {
                    return number.toInt();
                }
                return number;
            }

            var answer = ""
            if (factorA != "0") {
                val result = solveEquation(factorA.toDouble(), factorB.toDouble(), factorC.toDouble())
                when (result.count()) {
                    0 -> answer = "Это квадратное уравнение не имеет корней."
                    1 -> answer = "x1 = x2 = ${formatNumber(result[0])}"
                    2 -> answer = "x1 = ${formatNumber(result[0])}\r\nx2 = ${formatNumber(result[1])}"
                }
            }
            else {
                answer = "Это уравнение не является квадратным."
            }

            val alert = AlertDialog.Builder(this).setPositiveButton("Ok") { d, id->d.cancel() }
            alert.setMessage(answer).create()
            alert.show()
        }

        val solveEquationButton = findViewById<Button>(R.id.solveEquation)
        solveEquationButton.setOnClickListener(clickListener)
    }
}