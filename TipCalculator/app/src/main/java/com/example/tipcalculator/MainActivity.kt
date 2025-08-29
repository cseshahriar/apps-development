package com.example.tipcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    // define element type
    private lateinit var  billAmountEditText: TextInputEditText
    private lateinit var tipOptionsGroup: RadioGroup
    private lateinit var resultTextView: TextView
    private lateinit var tipAmountTextView: TextView
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // get element by id
        billAmountEditText = findViewById(R.id.bill_input_edit_text)
        tipOptionsGroup = findViewById(R.id.tipOptionGroups)
        tipAmountTextView = findViewById(R.id.tipAmountTextView)
        resultTextView = findViewById(R.id.resultTextView)
        calculateButton = findViewById(R.id.calculateButton)

        calculateButton.setOnClickListener {
            calculateTip();
        }
    }

    private fun calculateTip() {
        val billAmountStr = billAmountEditText.text.toString()

        if(billAmountStr.isEmpty()) {
            resultTextView.text = "Please enter a valid bill amount."
            return
        }

        val billAmount = billAmountStr.toDouble()
        val selectedTipId = tipOptionsGroup.checkedRadioButtonId

        if(selectedTipId == -1) {
            resultTextView.text = "Please select a tip percentage."
            return
        }

        val tipPercentage: Double = when (selectedTipId) {
            R.id.tip_10_radio_button -> 10.0
            R.id.tip_15_radio_button -> 15.0
            R.id.tip_20_radio_button -> 20.0
            else -> 0.0
        }

        val tipAmount = billAmount * (tipPercentage / 100)
        val totalAmount = billAmount + tipAmount
        tipAmountTextView.text = String.format("Tip AmountL \n$%.2f", tipAmount)
        resultTextView.text = String.format("Total AmountL \n$%.2f", totalAmount)
    }
}