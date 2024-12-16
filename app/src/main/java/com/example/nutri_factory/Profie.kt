package com.example.nutri_factory

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.InputStream

class Profie : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteAccountButton: Button
    private lateinit var editUsernameIcon: ImageView
    private lateinit var editPasswordIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profie)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteAccountButton = findViewById(R.id.deleteAccountButton)
        editUsernameIcon = findViewById(R.id.editUsernameIcon)
        editPasswordIcon = findViewById(R.id.editPasswordIcon)

        loadUserData()

        // Save updated user data
        saveButton.setOnClickListener {
            updateUserData()
        }

        // Confirm and delete user account
        deleteAccountButton.setOnClickListener {
            confirmDeleteAccount()
        }

        // Enable editing username and password
        editUsernameIcon.setOnClickListener {
            usernameEditText.isEnabled = true
        }

        editPasswordIcon.setOnClickListener {
            passwordEditText.isEnabled = true
        }
    }

    // Load stored user data from SharedPreferences
    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("USER_NAME", null)
        val savedPassword = sharedPreferences.getString("USER_PASSWORD", null)

        usernameEditText.setText(savedName ?: "")
        passwordEditText.setText(savedPassword ?: "")
    }

    // Update and save user data to SharedPreferences
    private fun updateUserData() {
        val updatedName = usernameEditText.text.toString()
        val updatedPassword = passwordEditText.text.toString()

        if (updatedName.isBlank() || updatedPassword.isBlank()) {
            showErrorDialog("Username or Password cannot be empty!")
            return
        }

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_NAME", updatedName)
        editor.putString("USER_PASSWORD", updatedPassword)
        editor.apply()

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }

    // Confirm and delete user account data
    private fun confirmDeleteAccount() {
        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteAccount()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Clear user data from SharedPreferences and reset the UI
    private fun deleteAccount() {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        usernameEditText.text.clear()
        passwordEditText.text.clear()

        Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
        // Navigate to Registration page after deleting account
        val intent = Intent(this, RegistrationActivity::class.java) // Replace RegistrationActivity with your actual registration activity class name
        startActivity(intent)
        finish()
    }

    // Show error dialog for invalid inputs
    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}