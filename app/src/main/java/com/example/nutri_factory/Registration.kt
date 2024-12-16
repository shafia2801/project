package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class RegistrationActivity : AppCompatActivity() {
    lateinit var radioGroupGender: RadioGroup
    lateinit var radioGroupDiet: RadioGroup
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val buttonAlreadyRegistered: Button = findViewById(R.id.buttonAlreadyRegistered)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        radioGroupDiet = findViewById(R.id.radioGroupDiet)

        buttonRegister.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextUsername).text.toString()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
            val phone = findViewById<EditText>(R.id.editTextPhone).text.toString()
            val email = findViewById<EditText>(R.id.editTextEmail).text.toString()

            if (!isValidUsername(username)) {
                showToast("Username must have at least 1 uppercase letter and 1 digit")
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                showToast("Password must be at least 6 characters long")
                return@setOnClickListener
            }

            if (!isValidPhoneNumber(phone)) {
                showToast("Phone number must be 10 digits long")
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                showToast("Email must be a valid Gmail address")
                return@setOnClickListener
            }

            val selectedGenderId = radioGroupGender.checkedRadioButtonId
            val gender = findViewById<RadioButton>(selectedGenderId)?.text.toString()


            // Get selected diet preference
            val selectedDietId = radioGroupDiet.checkedRadioButtonId
            val diet = findViewById<RadioButton>(selectedDietId)?.text.toString()

            // Save user details to SharedPreferences
            saveUserDetails(username, password, email, gender, diet)

            // Proceed to next page if all validations pass
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Show Toast indicating successful registration
            showToast("Successfully registered as $username")
        }

        buttonAlreadyRegistered.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidUsername(username: String): Boolean {
        return username.matches(Regex("^(?=.*[A-Z])(?=.*\\d).+\$"))
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.length == 10
    }

    private fun isValidEmail(email: String): Boolean {
        return email.endsWith("@gmail.com")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveUserDetails(username: String, password: String, email: String, gender: String, diet: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_NAME", username)
        editor.putString("USER_PASSWORD", password)
        editor.putString("USER_EMAIL", email)
        editor.putString("USER_GENDER", gender)
        editor.putString("USER_DIET", diet) // Save diet preference
        editor.apply()
    }
}