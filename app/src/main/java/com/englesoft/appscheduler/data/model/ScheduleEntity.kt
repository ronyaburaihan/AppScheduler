package com.englesoft.appscheduler.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.utils.toDrawable

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey
    val packageName: String,
    val name: String,
    val icon: ByteArray,
    val triggerTime: Long,
    val executed: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScheduleEntity

        if (name != other.name) return false
        if (packageName != other.packageName) return false
        if (!icon.contentEquals(other.icon)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + icon.contentHashCode()
        return result
    }

    fun toScheduleInfo(): ScheduleInfo {
        return ScheduleInfo(
            packageName = packageName,
            name = name,
            icon = icon.toDrawable(),
            triggerTime = triggerTime,
            executed = executed
        )
    }
}