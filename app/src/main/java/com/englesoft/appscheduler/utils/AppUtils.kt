package com.englesoft.appscheduler.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.englesoft.appscheduler.domain.model.AppInfo
import com.englesoft.appscheduler.receiver.AppLauncherReceiver

object AppUtils {
    @SuppressLint("QueryPermissionsNeeded")
    fun getInstalledApps(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return apps.mapNotNull { app ->
            if (app.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                AppInfo(
                    name = app.loadLabel(packageManager).toString(),
                    packageName = app.packageName,
                    icon = app.loadIcon(packageManager)
                )
            } else {
                null
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleApp(context: Context, packageName: String, triggerTime: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AppLauncherReceiver::class.java).apply {
            putExtra("package_name", packageName)
            putExtra("trigger_time", triggerTime)
        }
        val requestCode = triggerTime.hashCode()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }

    fun cancelSchedule(context: Context, packageName: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AppLauncherReceiver::class.java)
        val requestCode = packageName.hashCode()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
        }
    }
}