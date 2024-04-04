package org.nautilusapp.localstorage

import org.nautilusapp.nautilus.scouting.scoutingdata.Crescendo
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoAuto
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoHuman
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoRankingPoints
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStage
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStageState
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoTeleop

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
            auto_attempted = obj.auto.attempted,
            auto_scored = obj.auto.scored,
            teleopAmp = obj.teleop.ampNotes,
            teleopSpeakerAmp = obj.teleop.speakerAmped,
            teleopSpeakerUnamp = obj.teleop.speakerUnamped,
            competition = obj.competition,
            comments = obj.comments,
            brokeDown = obj.brokeDown,
            defensive = obj.defensive,
            score = obj.finalScore,
            matchNumber = obj.matchNumber,
            teamNumber = obj.teamNumber,
            penalty = obj.penaltyPointsEarned,
            melody = obj.ranking.melody,
            ensemble = obj.ranking.ensemble,
            rankingPoints = obj.rankingPoints,
            stageState = obj.stage.state.name,
            harmony = obj.stage.harmony,
            trap = obj.stage.trap,
            teamName = obj.teamName,
            tie = obj.tied,
            won = obj.won,
            created_by = obj.createdBy,
            coop = obj.coopertition,
            defense_rating = obj.defenseRating,
            human_skill = obj.human.rating,
            human_source = obj.human.source,
            rating = obj.rating
        )
    }

    fun insert(objs: List<Crescendo>) {
        objs.forEach { this.insert(it) }
    }
}

fun mapCrescendo(it: Crescendo_table): Crescendo {
    return Crescendo(
        _id = it.id,
        auto = CrescendoAuto(
            leave = it.auto_leave,
            attempted = it.auto_attempted,
            scored = it.auto_scored
        ),
        teleop = CrescendoTeleop(
            ampNotes = it.teleop_ampNotes,
            speakerAmped = it.teleop_speakerAmped,
            speakerUnamped = it.teleop_speakerUnamped
        ),
        competition = it.competition,
        comments = it.comments,
        brokeDown = it.brokeDown,
        defensive = it.defensive,
        finalScore = it.score,
        matchNumber = it.matchNumber,
        teamNumber = it.teamNumber,
        penaltyPointsEarned = it.penalty,
        ranking = CrescendoRankingPoints(
            melody = it.melody,
            ensemble = it.ensemble
        ),
        rankingPoints = it.rankingPoints,
        stage = CrescendoStage(
            state = try {
                CrescendoStageState.valueOf(it.stageState)
            } catch (_: Exception) {
                CrescendoStageState.NOT_PARKED
            },
            harmony = it.harmony,
            trap = it.trap
        ),
        teamName = it.teamName,
        tied = it.tie,
        won = it.won,
        createdBy = it.created_by,
        rating = it.rating,
        defenseRating = it.defense_rating,
        coopertition = it.coop,
        human = CrescendoHuman(
            rating = it.human_skill,
            source = it.human_source
        )
    )
}