package com.example.daydo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class NotificationSchedulerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Here you can add additional logic if you need to check if the challenge is completed.
        // For now, we simply schedule both notifications.
        scheduleNotification(context, 12, 0, "Perfect time to do your Do for the day", 1001)
        scheduleNotification(context, 20, 0, "It's getting late, do your task now!", 1002)
    }

    private fun scheduleNotification(
        context: Context,
        hour: Int,
        minute: Int,
        message: String,
        notificationId: Int
    ) {
        // Prepare the intent that triggers NotificationReceiver
        val notificationIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("notification_message", message)
            putExtra("notification_id", notificationId)
        }
        // Use conditional flags for compatibility
        val flags = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            notificationIntent,
            flags
        )

        // Set up the target time in the calendar
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        // If the target time is already past, schedule for the next day.
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
        }

        // Get the AlarmManager service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager

        // Use setExactAndAllowWhileIdle to ensure the alarm fires even in Doze mode
        alarmManager.setExactAndAllowWhileIdle(
            android.app.AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
