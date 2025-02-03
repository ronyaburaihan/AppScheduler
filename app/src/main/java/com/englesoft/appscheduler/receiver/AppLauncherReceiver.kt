package com.englesoft.appscheduler.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.englesoft.appscheduler.service.AppLauncherService

class AppLauncherReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.getStringExtra("package_name") ?: return
        val triggerTime = intent.getLongExtra("trigger_time", 0)
        if (triggerTime == 0L) {
            Log.e("AppLauncherReceiver", "Invalid trigger time")
            return
        }

        val serviceIntent = Intent(context, AppLauncherService::class.java).apply {
            putExtra("package_name", packageName)
            putExtra("trigger_time", triggerTime)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}