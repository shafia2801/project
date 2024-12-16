package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

class Logout : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
val imm:ImageView=findViewById(R.id.immm
)
        val btn: Button = findViewById(R.id.btn)
        val buttonLogout: Button = findViewById(R.id.buttonLogout)

        imm.setOnClickListener{
            val intent=Intent(this,Home::class.java)
            startActivity(intent)
        }
        buttonLogout.setOnClickListener {
            showConfirmationDialog(
                "Logout Confirmation",
                "Are you sure you want to logout? This will clear all your user data.",
                "Logout"
            ) {
                // Clear user data from SharedPreferences
                val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear() // Remove all data
                editor.apply() // Save changes

                // Redirect to Registration page
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
                finish() // Finish the current activity
            }
        }

        btn.setOnClickListener {
            showConfirmationDialog(
                "Navigate Confirmation",
                "Are you sure you want to go back to the Login page?",
                "Go to Login"
            ) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showConfirmationDialog(title: String, message: String, positiveButtonText: String, onPositiveClick: () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(title)
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton(positiveButtonText) { dialog, _ ->
            onPositiveClick() // Execute the specified action
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss() // Just dismiss the dialog
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}