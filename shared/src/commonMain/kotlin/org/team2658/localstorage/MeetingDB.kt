package org.team2658.localstorage

import org.team2658.nautilus.attendance.Meeting

internal class MeetingDB(db: AppDatabase) {
    private val dbQuery = db.meetingsQueries

    fun getAll(): List<Meeting> {
        return this.dbQuery.getAllWithUsername().executeAsList().map {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy,
                attendancePeriod = it.attendance_period,
                username = it.username
            )
        }
    }

    fun insert(meeting: Meeting) {
        this.dbQuery.insertOne(
            id = meeting._id,
            startTime = meeting.startTime,
            endTime = meeting.endTime,
            type = meeting.type,
            description = meeting.description,
            value_ = meeting.value.toLong(),
            createdBy = meeting.createdBy,
            attendance_period = meeting.attendancePeriod
        )
    }

    fun insert(meetings: List<Meeting>) {
        meetings.forEach { this.insert(it) }
    }

    fun refresh(new: List<Meeting>) {
        this.dbQuery.transaction {
            deleteAll()
            insert(new)
        }
    }

    fun delete(id: String) {
        this.dbQuery.removeOne(id)
    }

    fun delete(ids: List<String>) {
        ids.forEach { this.delete(it) }
    }

    fun deleteAll() {
        this.dbQuery.removeAll()
    }

    fun getCurrent(currentTime: Long): List<Meeting> {
        return this.dbQuery.getCurrentWithUsername(currentTime).executeAsList().map {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy,
                attendancePeriod = it.attendance_period,
                username = it.username
            )
        }
    }

    fun getOutdated(currentTime: Long): List<Meeting> {
        return this.dbQuery.getOutdatedWithUsername(currentTime).executeAsList().map {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy,
                attendancePeriod = it.attendance_period,
                username = it.username
            )
        }
    }

    fun getOne(id: String): Meeting? {
        return this.dbQuery.getOneWithUsername(id).executeAsOneOrNull()?.let {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy,
                attendancePeriod = it.attendance_period,
                username = it.username
            )
        }
    }
}