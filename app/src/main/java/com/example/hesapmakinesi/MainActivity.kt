package com.example.hesapmakinesi

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private var currentNumber = ""
    private var previousNumber = ""
    private var operation: String? = null
    private var resultCalculated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.result)

        // Sayı butonları
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val value = (it as Button).text.toString()
                if (resultCalculated) {
                    currentNumber = ""
                    resultCalculated = false
                }
                currentNumber += value
                resultText.text = currentNumber
            }
        }

        // İşlem butonları
        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            handleOperatorClick("÷")
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            handleOperatorClick("×")
        }

        findViewById<Button>(R.id.btnMinus).setOnClickListener {
            handleOperatorClick("-")
        }

        findViewById<Button>(R.id.btnPlus).setOnClickListener {
            handleOperatorClick("+")
        }

        // Eşittir
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            val num1 = previousNumber.toDoubleOrNull()
            val num2 = currentNumber.toDoubleOrNull()

            if (num1 != null && num2 != null && operation != null) {
                val result = when (operation) {
                    "+" -> num1 + num2
                    "-" -> num1 - num2
                    "×" -> num1 * num2
                    "÷" -> if (num2 != 0.0) num1 / num2 else "Hata"
                    else -> "Hata"
                }
                resultText.text = result.toString()
                resultCalculated = true
            }
        }

        // Temizle
        findViewById<Button>(R.id.btnC).setOnClickListener {
            currentNumber = ""
            previousNumber = ""
            operation = null
            resultText.text = "0"
        }

        // +/- tuşu
        findViewById<Button>(R.id.btnNegate).setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                if (currentNumber.startsWith("-")) {
                    currentNumber = currentNumber.substring(1)
                } else {
                    currentNumber = "-$currentNumber"
                }
                resultText.text = currentNumber
            }
        }

        // % tuşu
        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            val num = currentNumber.toDoubleOrNull()
            if (num != null) {
                currentNumber = (num / 100).toString()
                resultText.text = currentNumber
            }
        }
    }

    private fun handleOperatorClick(op: String) {
        if (currentNumber.isNotEmpty()) {
            previousNumber = currentNumber
            currentNumber = ""
            operation = op
        }
    }
}
