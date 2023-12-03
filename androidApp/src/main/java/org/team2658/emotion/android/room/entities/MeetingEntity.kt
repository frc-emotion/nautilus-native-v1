package org.team2658.emotion.android.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val startTime: Long, // Unix timestamp
    val endTime: Long, // Unix timestamp
    val type: String,
    val description: String,
    val value: Int,
    val createdBy: String,
    val createdByUsername: String?,
) {
    companion object {
        fun fromShared(meeting: org.team2658.emotion.attendance.Meeting, username: String?): MeetingEntity {
            return MeetingEntity(
                id = meeting._id,
                startTime = meeting.startTime,
                endTime = meeting.endTime,
                type = meeting.type,
                description = meeting.description,
                value = meeting.value,
                createdBy = meeting.createdBy,
                createdByUsername = username,
            )
        }

        fun toShared(meeting: MeetingEntity): Pair<org.team2658.emotion.attendance.Meeting, String?> {
            return Pair(org.team2658.emotion.attendance.Meeting(
                _id = meeting.id,
                startTime = meeting.startTime,
                endTime = meeting.endTime,
                type = meeting.type,
                description = meeting.description,
                value = meeting.value,
                createdBy = meeting.createdBy,
            ), meeting.createdByUsername)
        }
    }
}
