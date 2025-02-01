package com.englesoft.appscheduler.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.englesoft.appscheduler.domain.model.AppInfo

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
}