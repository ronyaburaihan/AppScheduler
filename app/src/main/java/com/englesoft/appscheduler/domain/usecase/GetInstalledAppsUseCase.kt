package com.englesoft.appscheduler.domain.usecase

import android.content.Context
import com.englesoft.appscheduler.domain.model.AppInfo
import com.englesoft.appscheduler.utils.AppUtils

class GetInstalledAppsUseCase(private val context: Context) {
    operator fun invoke(): List<AppInfo> {
        return AppUtils.getInstalledApps(context)
    }
}