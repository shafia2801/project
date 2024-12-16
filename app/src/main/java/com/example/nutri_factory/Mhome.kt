package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class Mhome : AppCompatActivity() {
    private lateinit var immm: ImageView
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var textViewResult: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mhome)

        editTextWeight = findViewById(R.id.editTextWeight)
        editTextHeight = findViewById(R.id.editTextHeight)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textViewResult = findViewById(R.id.textViewResult)

        immm = findViewById(R.id.i2)
        immm.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        buttonCalculate.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val weightStr = editTextWeight.text.toString()
        val heightStr = editTextHeight.text.toString()

        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            val weight = weightStr.toFloat()
            val (feet, inches) = parseHeight(heightStr)
            val heightMeters = feetAndInchesToMeters(feet, inches)

            if (heightMeters > 0) {
                val bmi = weight / (heightMeters * heightMeters)
                val bmiCategory = getBMICategory(bmi)

                // Retrieve diet preference from SharedPreferences
                val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val dietPreference = sharedPreferences.getString("USER_DIET", "Not Specified")

                // Navigate based on diet preference and BMI category
                when (dietPreference) {
                    "Vegetarian" -> {
                        when (bmiCategory) {
                            "Underweight" -> {
                                val intent = Intent(this, VegUnderweightActivity::class.java)
                                startActivity(intent)
                            }
                            "Normal weight" -> {
                                val intent = Intent(this, VegNormalWeightActivity::class.java)
                                startActivity(intent)
                            }
                            "Overweight" -> {
                                val intent = Intent(this, VegOverweightActivity::class.java)
                                startActivity(intent)
                            }
                            "Obese" -> {
                                val intent = Intent(this, VegObeseActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                    "Non-Vegetarian" -> {
                        when (bmiCategory) {
                            "Underweight" -> {
                                val intent = Intent(this, UnderweightActivity::class.java)
                                startActivity(intent)
                            }
                            "Normal weight" -> {
                                val intent = Intent(this, NormalWeightActivity::class.java)
                                startActivity(intent)
                            }
                            "Overweight" -> {
                                val intent = Intent(this, OverweightActivity::class.java)
                                startActivity(intent)
                            }
                            "Obese" -> {
                                val intent = Intent(this, ObeseActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                    else -> {
                        textViewResult.text = "Diet preference not set."
                    }
                }

                textViewResult.text = "Your BMI: %.2f\nCategory: $bmiCategory".format(bmi)
            } else {
                textViewResult.text = "Invalid height"
            }
        } else {
            textViewResult.text = "Please enter weight and height in feet and inches (e.g., 4.11)"
        }
    }

    private fun parseHeight(heightStr: String): Pair<Float, Float> {
        val parts = heightStr.split('.')
        val feet = parts[0].toFloat()
        val inches = if (parts.size > 1) parts[1].toFloat() else 0f
        return Pair(feet, inches)
    }

    private fun feetAndInchesToMeters(feet: Float, inches: Float): Float {
        val totalInches = (feet * 12) + inches
        return totalInches * 0.0254f
    }

    private fun getBMICategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal weight"
            bmi < 30 -> "Overweight"
            else -> "Obese"
        }
    }
}
