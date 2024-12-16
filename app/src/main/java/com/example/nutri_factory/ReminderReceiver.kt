package com.example.nutri_factory
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class ReminderReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        // Create a notification channel (for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "nutri_reminder_channel"
            val channelName = "Reminder Channel"
            val channelDescription = "Channel for Nutri_Factory reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Extract the reminder type from the intent
        val reminderType = intent.getStringExtra("reminder_type") ?: "Meal"
        val reminderText = when (reminderType) {
            "Breakfast" -> "It's time for breakfast!"
            "Lunch" -> "It's time for lunch!"
            "Dinner" -> "It's time for dinner!"
            else -> "Stay hydrated! Drink some water."
        }

        // Create notification
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(context, "nutri_reminder_channel")
            .setSmallIcon(R.drawable.ic_notification) // Replace with your own notification icon
            .setContentTitle("${reminderType} Reminder")
            .setContentText(reminderText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(getNotificationId(reminderType), notificationBuilder.build())
    }

    private fun getNotificationId(reminderType: String): Int {
        return when (reminderType) {
            "Breakfast" -> 1
            "Lunch" -> 2
            "Dinner" -> 3
            else -> 0
        }
    }
}