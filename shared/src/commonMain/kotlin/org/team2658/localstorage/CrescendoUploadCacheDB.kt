package org.team2658.localstorage

import org.team2658.localstorage.uploadcache.CrescendoUpload


class CrescendoUploadCacheDB(db: AppDatabase) {
    private val dbQuery = db.crescendoUploadCacheQueries

    fun getAll(): List<CrescendoUpload> {
        return this.dbQuery.getAll().executeAsList()
    }

    fun insert(obj: CrescendoUpload) {
        this.dbQuery.insert(
            autoAmp = obj.autoAmp,
            autoLeave = obj.autoLeave,
            autoSpeaker = obj.autoSpeaker,
            brokeDown = obj.brokeDown,
            comments = obj.comments,
            competition = obj.competition,
            defensive = obj.defensive,
            ensemble = obj.ensemble,
            harmony = obj.harmony,
            matchNumber = obj.matchNumber,
            melody = obj.melody,
            penalty = obj.penalty,
            rankingPoints = obj.rankingPoints,
            score = obj.score,
            stageState = obj.stageState,
            teamNumber = obj.teamNumber,
            teleopAmp = obj.teleopAmp,
            teleopSpeakerAmp = obj.teleopSpeakerAmp,
            teleopSpeakerUnamp = obj.teleopSpeakerUnamp,
            tie = obj.tie,
            trapNotes = obj.trapNotes,
            won = obj.won
        )
    }

    fun insert(objs: List<CrescendoUpload>) {
        objs.forEach { this.insert(it) }
    }

    fun deleteAll() {
        this.dbQuery.deleteAll()
    }

    fun delete(ids: List<Long>) {
        ids.forEach { this.delete(it) }
    }

    fun delete(id: Long) {
        this.dbQuery.delete(id)
    }

    fun getOne(id: Long): CrescendoUpload? {
        return this.dbQuery.get(id).executeAsOneOrNull()
    }
}