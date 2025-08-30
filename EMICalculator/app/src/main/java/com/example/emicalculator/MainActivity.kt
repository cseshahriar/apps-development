package com.example.emicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Makes UI layout extend into system bars (status bar, nav bar)
        setContentView(R.layout.activity_main)

        // Bind UI elements from the XML layout using their IDs
        val principleEditText: EditText = findViewById(R.id.principle_amount) // Loan amount input
        val interestRateEditText: EditText = findViewById(R.id.interest_rate) // Annual interest rate input
        val tenureMonthEditText: EditText = findViewById(R.id.tenure_month)   // Loan tenure in months input
        val calculateButton : Button = findViewById(R.id.calculate_button)    // Calculate button
        val resultTextView: TextView = findViewById(R.id.result)              // Result text display

        // When the user clicks the calculate button
        calculateButton.setOnClickListener {
            // Convert user inputs to numbers safely (null if invalid)
            val principle = principleEditText.text.toString().toDoubleOrNull()
            val annualInterestRate = interestRateEditText.text.toString().toDoubleOrNull()
            val tenureMonths = tenureMonthEditText.text.toString().toIntOrNull() ?: 0 // Default to 0 if invalid

            // Validate inputs before calculation
            if (principle != null && annualInterestRate != null && tenureMonths > 0) {
                // Convert annual rate (%) to monthly rate (decimal)
                val monthlyInterestRate = annualInterestRate / 12 / 100

                // Call EMI calculation function
                val emi = calculateEMI(principle, monthlyInterestRate, tenureMonths)

                // Display EMI result formatted to 2 decimal places
                resultTextView.text = "Your monthly EMI is: %.2f".format(emi)
            } else {
                // Show error if inputs are invalid
                resultTextView.text = "Please enter valid inputs"
            }
        }
    }

    /**
     * Function to calculate EMI
     * @param principle Loan amount
     * @param rate Monthly interest rate (decimal, e.g. 0.01 for 1%)
     * @param tenure Number of months
     * @return EMI (monthly installment amount)
     */
    private fun calculateEMI(principle: Double, rate: Double, tenure: Int): Double {
        return if (rate == 0.0) {
            // If interest rate is 0, EMI is just principal divided by months
            principle / tenure
        } else {
            // Standard EMI formula:
            // EMI = [P × R × (1+R)^N] / [(1+R)^N – 1]
            val numerator = principle * rate * Math.pow(1 + rate, tenure.toDouble())
            val denominator = Math.pow(1 + rate, tenure.toDouble()) - 1
            numerator / denominator
        }
    }
}
