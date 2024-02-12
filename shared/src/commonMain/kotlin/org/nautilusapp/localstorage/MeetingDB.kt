package org.nautilusapp.localstorage

import org.nautilusapp.nautilus.attendance.Meeting

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
                username = it.username,
                isArchived = it.archived
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
            value = meeting.value.toLong(),
            createdBy = meeting.createdBy,
            attendance_period = meeting.attendancePeriod,
            archived = meeting.isArchived,
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
                username = it.username,
                isArchived = it.archived
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
                username = it.username,
                isArchived = it.archived
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
                username = it.username,
                isArchived = it.archived
            )
        }
    }

    fun getArchived(): List<Meeting> {
        return this.dbQuery.getArchivedWithUsername().executeAsList().map {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy,
                attendancePeriod = it.attendance_period,
                username = it.username,
                isArchived = it.archived
            )
        }
    }
}