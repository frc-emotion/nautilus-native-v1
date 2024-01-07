package org.team2658.localstorage

import org.team2658.emotion.attendance.Meeting

internal class MeetingDB(factory: DatabaseDriverFactory) {
    private val database = Meetings(factory.createDriver())
    private val dbQuery = database.meetingsQueries

    fun getAll(): List<Meeting> {
        return this.dbQuery.getAll().executeAsList().map {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy
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
            createdBy = meeting.createdBy
        )
    }

    fun insert(meetings: List<Meeting>) {
        meetings.forEach { this.insert(it) }
    }

    fun delete(meeting: Meeting) {
        this.dbQuery.removeOne(meeting._id)
    }

    fun delete(meetings: List<Meeting>) {
        meetings.forEach { this.delete(it) }
    }

    fun deleteAll() {
        this.dbQuery.removeAll()
    }

    fun getCurrent(currentTime: Long): List<Meeting> {
        return this.dbQuery.getCurrent(currentTime).executeAsList().map {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy
            )
        }
    }

    fun getOutdated(currentTime: Long): List<Meeting> {
        return this.dbQuery.getOutdated(currentTime).executeAsList().map {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy
            )
        }
    }

    fun getOne(id: String): Meeting? {
        return this.dbQuery.getOne(id).executeAsOneOrNull()?.let {
            Meeting(
                _id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                type = it.type,
                description = it.description,
                value = it.value_.toInt(),
                createdBy = it.createdBy
            )
        }
    }
}