package com.example.interestcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var principalInput: EditText
    private lateinit var interestInput: EditText
    private lateinit var timeInput: EditText
    private lateinit var totalAmount: EditText
    private lateinit var calculateButton: Button
    private lateinit var autoCompleteTextView: AutoCompleteTextView    // For DropDown Menu
    private lateinit var cpdFreq: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets -> // Change here
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        principalInput = findViewById(R.id.principal_input)
        interestInput = findViewById(R.id.interest_input)
        timeInput = findViewById(R.id.time_input)
        totalAmount = findViewById(R.id.total_amount)
        calculateButton = findViewById(R.id.calculate_button)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)    // for DropDown Menu
        cpdFreq = findViewById(R.id.cpdFreq)

        calculateButton.setOnClickListener {
            val selectedInterestType = autoCompleteTextView.text.toString()

            if (selectedInterestType == "Simple Interest") {
                calSimpleInterest()
            } else if (selectedInterestType == "Compound Interest") {
                calCompoundingInterest()
            }
        }

        // For DropDown Menu
        val interestTypes = resources.getStringArray(R.array.interestType)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, interestTypes)
        autoCompleteTextView.setAdapter(adapter)

        val compoundingFreq = resources.getStringArray(R.array.compoundingFreq)
        val cpdAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, compoundingFreq)
        cpdFreq.setAdapter(cpdAdapter)

    }
    private fun calSimpleInterest(){
        val principalAmt = principalInput.text.toString().toInt()
        val interestRate = interestInput.text.toString().toFloat()
        val timePeriod = timeInput.text.toString().toFloat()

        val finalAmount = ((principalAmt * interestRate * timePeriod) / 100) + principalAmt
        totalAmount.setText(finalAmount.toString())
    }

    @SuppressLint("DefaultLocale")
    private fun calCompoundingInterest() {
        val principalAmt = principalInput.text.toString().toInt()
        val interestRate = interestInput.text.toString().toFloat()
        val timePeriod = timeInput.text.toString().toFloat()

        val selectedCpdFreq = cpdFreq.text.toString()
        val n: Int = when (selectedCpdFreq) {
            "Annually" -> {
                1
            }
            "Semi-Annually" -> {
                2
            }
            "Quarterly" -> {
                4
            }
            "Monthly" -> {
                12
            }
            "Weekly" -> {
                52
            }
            else -> {
                1                 // Default Annually
            }
        }
        val finalAmount = principalAmt * (1 + (interestRate / (n * 100)).toDouble()).pow((n * timePeriod).toDouble())
        totalAmount.setText(String.format("%.2f", finalAmount))

    }
}
