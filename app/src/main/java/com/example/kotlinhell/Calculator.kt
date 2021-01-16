package com.example.kotlinhell

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class Calculator : AppCompatActivity() {
    var currentOperation = ""
    var isLastButtonEquality = false
    var leftOperand = 0.toDouble()
    var rightOperand = 0.toDouble()
    var pressedButtonId = -1;
    var isRepeatLastOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
    }

    fun formatNumber(number: Double): Number {
        if (number % 1 == 0.0) {
            return number.toInt();
        }
        return number;
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun handleNumberClick(view: View) {
        val pressedButton = view as Button
        val clickedButtonText = pressedButton.text.toString();

        val output = findViewById<TextView>(R.id.calculatorOutput)
        val currentOutput = output.text.toString()

        if (clickedButtonText == ",") {
            if (currentOutput.indexOf(",") == -1) {
                output.text = "$currentOutput$clickedButtonText"
            }
        }
        else if (currentOutput == "0") {
            output.text = clickedButtonText
        }
        else {
            if (this.pressedButtonId != -1) {
                output.text = clickedButtonText
            }
            else {
                output.text = "$currentOutput$clickedButtonText"
            }
        }

        if (pressedButtonId != -1) {
            lowlightActionButton(this.pressedButtonId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun highlightActionButton(buttonId: Int) {
        val foundButton = findViewById<Button>(buttonId)
        if (this.pressedButtonId != -1) {
            lowlightActionButton(this.pressedButtonId)
        }
        foundButton.background.setTint(Color.parseColor("#ffffff"))
        foundButton.setTextColor(Color.parseColor("#f5ac58"))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun lowlightActionButton(buttonId: Int) {
        this.pressedButtonId = -1
        val foundButton = findViewById<Button>(buttonId)
        foundButton.background.setTint(Color.parseColor("#f5ac58"))
        foundButton.setTextColor(Color.parseColor("#ffffff"))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun handleMathAction(view: View) {
        val output = findViewById<TextView>(R.id.calculatorOutput)
        val currentOutput = output.text.toString()

        val pressedButton = view as Button;
        if (pressedButton.id != R.id.equalityButton) {
            highlightActionButton(pressedButton.id)
            this.pressedButtonId = pressedButton.id
            this.rightOperand = 0.0
        }
        else if (this.pressedButtonId != -1) {
            lowlightActionButton(this.pressedButtonId)
        }

        if (pressedButton.id == R.id.equalityButton) {
            if (this.rightOperand == 0.0) {
                this.rightOperand = currentOutput.toDouble()
            }
        }

        if (this.currentOperation != "") {
            var rightNumber = currentOutput.toDouble()
            if (this.rightOperand != 0.0) {
                rightNumber = this.rightOperand
            }

            if ((pressedButton.id != R.id.equalityButton && !this.isLastButtonEquality) || (pressedButton.id == R.id.equalityButton)) {
                when (this.currentOperation) {
                    "+" -> {
                        val result = formatNumber(this.leftOperand + rightNumber)
                        this.leftOperand = result.toDouble()
                        output.text = result.toString()
                    }
                    "-" -> {
                        val result = formatNumber(this.leftOperand - rightNumber)
                        this.leftOperand = result.toDouble()
                        output.text = result.toString()
                    }
                    "*" -> {
                        val result = formatNumber(this.leftOperand * rightNumber)
                        this.leftOperand = result.toDouble()
                        output.text = result.toString()
                    }
                    "/" -> {
                        val result = formatNumber(this.leftOperand / rightNumber)
                        this.leftOperand = result.toDouble()
                        output.text = result.toString()
                    }
                }
            }
            else {
                this.leftOperand = currentOutput.toDouble()
            }
        }
        else {
            this.leftOperand = currentOutput.toDouble()
        }

        if (pressedButton.id != R.id.equalityButton) {
            this.currentOperation = pressedButton.text.toString()
        }

        this.isLastButtonEquality = pressedButton.id == R.id.equalityButton
        /*this.isActionPressed = true;
        val pressedButton = view as Button

        val output = findViewById<TextView>(R.id.calculatorOutput)
        val currentOutput = output.text.toString()

        if (this.currentOperation == "") {
            this.leftOperand = currentOutput.toDouble()
        }
        else {
            val rightNumber = currentOutput.toDouble()
            when (this.currentOperation) {
                "/" -> output.text = formatNumber(this.leftOperand / rightNumber).toString()
                "*" -> output.text = formatNumber(this.leftOperand * rightNumber).toString()
                "-" -> output.text = formatNumber(this.leftOperand - rightNumber).toString()
                "+" -> output.text = formatNumber(this.leftOperand + rightNumber).toString()
            }
        }

        val operationSymbol = pressedButton.text.toString();
        if (operationSymbol != "=") {
            this.currentOperation = pressedButton.text.toString();
        }
        else {
            if (rightOperand == 0.0) {
                rightOperand = currentOutput.toDouble()
            }
        }*/
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun clearAll(view: View) {
        val output = findViewById<TextView>(R.id.calculatorOutput)
        output.text = "0"
        this.currentOperation = ""
        this.leftOperand = 0.0
        this.rightOperand = 0.0
        if (this.pressedButtonId != -1) {
            lowlightActionButton(this.pressedButtonId)
        }
        this.pressedButtonId = -1
        this.isRepeatLastOperation = false
        this.isLastButtonEquality = false
    }

    fun changeSign(view: View) {
        val output = findViewById<TextView>(R.id.calculatorOutput)
        val currentOutput = output.text.toString()

        if (currentOutput[0] == '-') {
            output.text = currentOutput.substring(1)
        }
        else {
            output.text = "-$currentOutput"
        }
    }

    fun getOnePercent(view: View) {
        val output = findViewById<TextView>(R.id.calculatorOutput)
        val currentOutput = output.text.toString().toDouble()
        output.text = "${currentOutput / 100}"
    }
}