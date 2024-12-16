package com.example.nutri_factory

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        // Create notification channel
        createNotificationChannel()

        // Schedule notifications for breakfast and lunch
        scheduleDailyNotifications()

        android.os.Handler().postDelayed({
            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
            finish()
        }, 1000)
        Toast.makeText(this, "Welcome to Nutri_Factory", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminder Channel"
            val descriptionText = "Channel for Nutri_Factory reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("nutri_reminder_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleDailyNotifications() {
        val notificationIntent = Intent(this, NotificationReceiver::class.java)


            // Breakfast at 8 AM
            scheduleNotification("Breakfast", 8, 0)

            // Lunch at 1 PM
            scheduleNotification("Lunch", 13, 0)

            // Dinner at 9 PM
            scheduleNotification("Dinner", 20, 0)

        // Water Reminder at 11 AM
        scheduleNotification( "Water", 7, 0)
        // Water Reminder at 11 AM
        scheduleNotification( "Water", 12, 0)
        // Water Reminder at 11 AM
        scheduleNotification( "Water", 19, 0)
        }

        private fun scheduleNotification(reminderType: String, hour: Int, minute: Int) {
            val notificationIntent = Intent(this, ReminderReceiver::class.java).apply {
                putExtra("reminder_type", reminderType) // Pass reminder type to the intent
            }

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                hour * 100 + minute, // Unique request code
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Use FLAG_IMMUTABLE for API 31+
            )

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DATE, 1) // If the time has already passed today, schedule for tomorrow
                }
            }

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

    private fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()
        } else {
            true // No need to check for versions below S
        }
    }
}