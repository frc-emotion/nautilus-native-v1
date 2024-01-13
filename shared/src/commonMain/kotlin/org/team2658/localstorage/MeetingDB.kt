package org.team2658.localstorage

import org.team2658.emotion.attendance.Meeting

internal class MeetingDB(db: AppDatabase) {
    private val dbQuery = db.meetingsQueries

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

    fun getAllWithUsernames(): List<Pair<Meeting, String?>> {
        return this.dbQuery.getAllWithUsername().executeAsList().map {
            Pair(
                Meeting(
                    _id = it.id,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    type = it.type,
                    description = it.description,
                    value = it.value_.toInt(),
                    createdBy = it.createdBy
                ),
                it.username
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

    fun getCurrentWithUsernames(currentTime: Long): List<Pair<Meeting, String?>> {
        return this.dbQuery.getCurrentWithUsername(currentTime).executeAsList().map {
            Pair(
                Meeting(
                    _id = it.id,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    type = it.type,
                    description = it.description,
                    value = it.value_.toInt(),
                    createdBy = it.createdBy
                ),
                it.username
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

    fun getOutdatedWithUsernames(currentTime: Long): List<Pair<Meeting, String?>> {
        return this.dbQuery.getOutdatedWithUsername(currentTime).executeAsList().map {
            Pair(
                Meeting(
                    _id = it.id,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    type = it.type,
                    description = it.description,
                    value = it.value_.toInt(),
                    createdBy = it.createdBy
                ),
                it.username
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

    fun getOneWithUsername(id: String): Pair<Meeting, String?>? {
        return this.dbQuery.getOneWithUsername(id).executeAsOneOrNull()?.let {
            Pair(
                Meeting(
                    _id = it.id,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    type = it.type,
                    description = it.description,
                    value = it.value_.toInt(),
                    createdBy = it.createdBy
                ),
                it.username
            )
        }
    }
}