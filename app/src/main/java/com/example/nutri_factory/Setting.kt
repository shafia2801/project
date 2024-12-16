package com.example.nutri_factory

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.app.NotificationCompat


class Setting : AppCompatActivity() {

        lateinit var sharedPreferences: SharedPreferences
        lateinit var switchNotifications: Switch
        lateinit var radioGroupTheme: RadioGroup
        lateinit var imageView: ImageView

    lateinit var profileName: TextView
    lateinit var profileEmail: TextView
    lateinit var profileImage: ImageView
    lateinit var buttonChangeProfileImage: Button

    lateinit var ratingBar: RatingBar
    lateinit var editTextReview: EditText
    lateinit var buttonSendEmail: Button

    lateinit var buttonFaq: Button

    private val REQUEST_CODE_STORAGE_PERMISSION = 1

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            // Apply saved theme before activity creation
            applySavedTheme()

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_setting)

            sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

            // Initialize UI components
            switchNotifications = findViewById(R.id.switchNotifications)
            radioGroupTheme = findViewById(R.id.radioGroupTheme)
            profileName = findViewById(R.id.profileName)
            profileEmail = findViewById(R.id.profileEmail)
            profileImage = findViewById(R.id.profileImage)
            buttonFaq = findViewById(R.id.buttonFaq)
buttonChangeProfileImage=findViewById(R.id.buttonChangeImage)
            ratingBar = findViewById(R.id.ratingBar)
            editTextReview = findViewById(R.id.editTextReview)
            buttonSendEmail = findViewById(R.id.buttonSubmitFeedback)
            imageView=findViewById(R.id.ima)

            // Load saved preferences
            loadPreferences()

            // Set profile data
            setProfileData()


imageView.setOnClickListener{
    val intent=Intent(this,Home::class.java)
    startActivity(intent)
}

            // Notification toggle listener
            switchNotifications.setOnCheckedChangeListener { _, isChecked ->
                saveNotificationPreference(isChecked)
            }

            // Theme selection listener
            radioGroupTheme.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radioLight -> applyTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    R.id.radioDark -> applyTheme(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

            buttonFaq.setOnClickListener {
                showFaqDialog()
            }

            buttonChangeProfileImage.setOnClickListener {
                openGalleryForImage()
            }
// Email button click listener
            buttonSendEmail.setOnClickListener {
                sendEmail()
            }
        }

    private fun sendEmail() {
        val recipient = "shafia2801@gmail.com"
        val subject = "Feedback from Nutri_Factory App"
        val message = editTextReview.text.toString().trim() // Get feedback from EditText

        // Ensure the message is not empty
        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter feedback before sending.", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the email intent
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822" // MIME type for email
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        // Check if there is at least one app that can handle this intent
        val packageManager = packageManager
        val activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val isIntentSafe = activities.isNotEmpty()

        if (isIntentSafe) {
            // Start the email client chooser
            startActivity(Intent.createChooser(intent, "Send email using:"))
            // Trigger notification after sending email
            triggerNotification("Thank you for your feedback!")
        } else {
            // Handle case where no email app is available
            Toast.makeText(this, "No email app found. Please install an email client.", Toast.LENGTH_LONG).show()
        }
    }

    private fun triggerNotification(message: String) {
        val channelId = "feedback_reminder_channel"
        val notificationId = 2

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Feedback Reminder Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Use your notification icon here
            .setContentTitle("Feedback Submitted")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notificationId, notification)
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri = data.data!!
            profileImage.setImageURI(imageUri)

            with(sharedPreferences.edit()) {
                putString("PROFILE_IMAGE_URI", imageUri.toString())
                apply()
            }         }
    }


    // Apply the saved theme when the activity starts
        private fun applySavedTheme() {
            sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val theme = sharedPreferences.getString("THEME", "light")
            if (theme == "light") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
    }

        // Load saved preferences
        private fun loadPreferences() {
            // Load notification preference
            val isNotificationsEnabled = sharedPreferences.getBoolean("NOTIFICATIONS", true)
            switchNotifications.isChecked = isNotificationsEnabled

            // Load theme preference
            val theme = sharedPreferences.getString("THEME", "light")
            if (theme == "light") {
                radioGroupTheme.check(R.id.radioLight)
            } else {
                radioGroupTheme.check(R.id.radioDark)
            }
        }

        // Save notification preference
        private fun saveNotificationPreference(isEnabled: Boolean) {
            with(sharedPreferences.edit()) {
                putBoolean("NOTIFICATIONS", isEnabled)
                apply()
            }
        }

        // Save and set the theme mode
        private fun applyTheme(themeMode: Int) {
            AppCompatDelegate.setDefaultNightMode(themeMode)
            with(sharedPreferences.edit()) {
                putString("THEME", if (themeMode == AppCompatDelegate.MODE_NIGHT_YES) "dark" else "light")
                apply()
            }

            // Recreate activity to apply theme change
            recreate()
        }
    // Set profile data
    private fun setProfileData() {
        // Fetch profile data from SharedPreferences
        val name = sharedPreferences.getString("USER_NAME", "John Doe")
        val email = sharedPreferences.getString("USER_EMAIL", "john.doe@example.com")
        val gender = sharedPreferences.getString("USER_GENDER", "male")

        Log.d("SettingActivity", "Gender: $gender")
        // Set profile data
        profileName.text = name
        profileEmail.text = email

        // Set profile image based on gender
        profileImage.setImageResource(
            if (gender?.equals("male", ignoreCase = true) == true) R.drawable.male else R.drawable.girl
        )
    }
    // Show FAQ dialog
    private fun showFaqDialog() {
        val faqContent = """
 1. What is Nutri_Factory?
                        Nutri_Factory is an innovative app designed to make healthy eating simple, personalized, and enjoyable. We combine the latest scientific research with cutting-edge technology to provide personalized nutrition advice tailored to your unique needs, based on factors such as age and gender.

2. How does Nutri_Factory work?
                         Our app analyzes your age and gender to offer customized food recommendations and portion sizes, ensuring you receive the nutrients your body needs to thrive. We use advanced algorithms and scientific research to provide you with accurate and personalized nutrition advice.

3. What features does Nutri_Factory offer?
              Nutri_Factory offers a range of features, including: Personalized Food Recommendations: Tailored suggestions based on your age and gender.Customizable Reminders: Set reminders to drink water, eat regular meals, and incorporate specific foods into your diet.Easy-to-Use Interface: A user-friendly app that provides all the information you need at your fingertips.
4. How can I set up reminders in Nutri_Factory?
                            You can easily set up reminders within the app by accessing the reminder settings. Simply choose your preferences for hydration, meal times, and specific foods, and we’ll send you gentle nudges at the times that work best for you.

5. Is Nutri_Factory suitable for everyone?
                             Yes, Nutri_Factory is designed to cater to a wide range of users, whether you're looking to maintain a healthy lifestyle, manage weight, or optimize your nutritional intake. Our app provides personalized recommendations to fit your specific needs.

6. How do I get started with Nutri_Factory?
                              To get started, download the Nutri_Factory app from the app store and follow the registration process. Once registered, you can input your details, set your preferences, and begin receiving personalized nutrition advice and reminders.
        """.trimIndent()

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("FAQ")
        dialogBuilder.setMessage(faqContent)
        dialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        dialogBuilder.create().show()
    }
}





