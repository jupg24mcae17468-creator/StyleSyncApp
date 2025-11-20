package com.example.stylesync.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.stylesync.R
import java.util.concurrent.TimeUnit

private const val CHANNEL_ID = "stylesync_appointments"
private const val CHANNEL_NAME = "Appointments"
private const val NOTIFICATION_ID = 1

class AppointmentNotificationHelper(private val context: Context) {

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "StyleSync appointment reminders"
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleAppointmentReminder(
        appointmentId: Int,
        customerName: String,
        stylistName: String,
        appointmentTime: String,
        delayInMillis: Long
    ) {
        val inputData = Data.Builder()
            .putInt("appointmentId", appointmentId)
            .putString("customerName", customerName)
            .putString("stylistName", stylistName)
            .putString("appointmentTime", appointmentTime)
            .build()

        val notificationWork = OneTimeWorkRequestBuilder<AppointmentReminderWorker>()
            .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "appointment_$appointmentId",
                ExistingWorkPolicy.REPLACE,
                notificationWork
            )
    }

    fun cancelAppointmentReminder(appointmentId: Int) {
        WorkManager.getInstance(context)
            .cancelUniqueWork("appointment_$appointmentId")
    }
}

class AppointmentReminderWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val appointmentId = inputData.getInt("appointmentId", 0)
        val customerName = inputData.getString("customerName") ?: return Result.failure()
        val stylistName = inputData.getString("stylistName") ?: return Result.failure()
        val appointmentTime = inputData.getString("appointmentTime") ?: return Result.failure()

        showNotification(
            title = "Upcoming Appointment",
            message = "Hey $customerName! Don't forget your StyleSync appointment at $appointmentTime with $stylistName."
        )

        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_scissors)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
            }
        } else {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }
    }
}
