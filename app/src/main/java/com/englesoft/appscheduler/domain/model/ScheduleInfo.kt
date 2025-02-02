package com.englesoft.appscheduler.domain.model

import com.englesoft.appscheduler.data.model.ScheduleEntity
import com.englesoft.appscheduler.utils.toByteArray

data class ScheduleInfo(
    val packageName: String,
    val name: String,
    val icon: android.graphics.drawable.Drawable,
    val triggerTime: Long,
    val executed: Boolean = false
) {
    fun toScheduleEntity(): ScheduleEntity {
        return ScheduleEntity(
            packageName = packageName,
            name = name,
            icon = icon.toByteArray(),
            triggerTime = triggerTime,
            executed = executed
        )
    }
}
