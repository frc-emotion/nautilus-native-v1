package org.team2658.localstorage

import org.team2658.nautilus.scouting.scoutingdata.Crescendo
import org.team2658.nautilus.scouting.scoutingdata.CrescendoAuto
import org.team2658.nautilus.scouting.scoutingdata.CrescendoRankingPoints
import org.team2658.nautilus.scouting.scoutingdata.CrescendoStage
import org.team2658.nautilus.scouting.scoutingdata.CrescendoStageState
import org.team2658.nautilus.scouting.scoutingdata.CrescendoTeleop

class CrescendoDB(db: AppDatabase) {
    private val dbQuery = db.crescendosQueries

    fun getAll(): List<Crescendo> {
        return this.dbQuery.getAll().executeAsList().map {
            mapCrescendo(it)
        }
    }

    fun getOne(id: String): Crescendo? {
        return this.dbQuery.getOne(id).executeAsOneOrNull()?.let {
            mapCrescendo(it)
        }
    }

    fun deleteAll() {
        this.dbQuery.deleteAll()
    }

    fun delete(id: String) {
        this.dbQuery.deleteOne(id)
    }

    fun delete(ids: List<String>) {
        ids.forEach { this.delete(it) }
    }

    fun insert(obj: Crescendo) {
        this.dbQuery.insert(
            id = obj._id,
            autoLeave = obj.auto.leave,
            autoAmp = obj.auto.ampNotes.toLong(),
            autoSpeaker = obj.auto.speakerNotes.toLong(),
            teleopAmp = obj.teleop.ampNotes.toLong(),
            teleopSpeakerAmp = obj.teleop.speakerAmped.toLong(),
            teleopSpeakerUnamp = obj.teleop.speakerUnamped.toLong(),
            competition = obj.competition,
            comments = obj.comments,
            brokeDown = obj.brokeDown,
            defensive = obj.defensive,
            score = obj.finalScore.toLong(),
            matchNumber = obj.matchNumber.toLong(),
            teamNumber = obj.teamNumber.toLong(),
            penalty = obj.penaltyPointsEarned.toLong(),
            melody = obj.ranking.melody,
            ensemble = obj.ranking.ensemble,
            rankingPoints = obj.rankingPoints.toLong(),
            stageState = obj.stage.state.name,
            harmony = obj.stage.harmony.toLong(),
            trapNotes = obj.stage.trapNotes.toLong(),
            teamName = obj.teamName,
            tie = obj.tied,
            won = obj.won
        )
    }

    fun insert(objs: List<Crescendo>) {
        objs.forEach { this.insert(it) }
    }
}

fun mapCrescendo(it: org.team2658.localstorage.Crescendo): Crescendo {
    return Crescendo(
        _id = it.id,
        auto = CrescendoAuto(
            leave = it.autoLeave,
            ampNotes = it.autoAmp.toInt(),
            speakerNotes = it.autoSpeaker.toInt()
        ),
        teleop = CrescendoTeleop(
            ampNotes = it.teleopAmp.toInt(),
            speakerAmped = it.teleopSpeakerAmp.toInt(),
            speakerUnamped = it.teleopSpeakerUnamp.toInt()
        ),
        competition = it.competition,
        comments = it.comments,
        brokeDown = it.brokeDown,
        defensive = it.defensive,
        finalScore = it.score.toInt(),
        matchNumber = it.matchNumber.toInt(),
        teamNumber = it.teamNumber.toInt(),
        penaltyPointsEarned = it.penalty.toInt(),
        ranking = CrescendoRankingPoints(
            melody = it.melody,
            ensemble = it.ensemble
        ),
        rankingPoints = it.rankingPoints.toInt(),
        stage = CrescendoStage(
            state = try { CrescendoStageState.valueOf(it.stageState) } catch(_: Exception) { CrescendoStageState.NOT_PARKED },
            harmony = it.harmony.toInt(),
            trapNotes = it.trapNotes.toInt()
        ),
        teamName = it.teamName,
        tied = it.tie,
        won = it.won
    )
}