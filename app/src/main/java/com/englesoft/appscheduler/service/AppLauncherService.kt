package com.englesoft.appscheduler.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.englesoft.appscheduler.R
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class AppLauncherService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val packageName = intent?.getStringExtra("package_name") ?: return START_NOT_STICKY
        val triggerTime = intent.getLongExtra("trigger_time", 0)

        startForeground(1, createNotification())

        launchApp(packageName)
        updateExecutedStatus(triggerTime)

        Handler(Looper.getMainLooper()).postDelayed({
            stopSelf()
        }, 5000)

        return START_NOT_STICKY
    }

    private fun createNotification(): Notification {
        val channelId = "app_launcher_service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "App Launcher Service", NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Launching App")
            .setContentText("Your app is being launched in the background")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    private fun launchApp(packageName: String) {
        val packageManager = packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(launchIntent)
        } else {
            Log.e("AppLauncherService", "Failed to launch app: $packageName")
        }
    }

    private fun updateExecutedStatus(timeStamp: Long) {
        val scheduleRepository: ScheduleRepository by inject()
        runBlocking {
            scheduleRepository.markAsExecuted(timeStamp)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
