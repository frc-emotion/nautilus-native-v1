package org.nautilusapp.localstorage

import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoAuto
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoHuman
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoRankingPoints
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStage
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoStageState
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoSubmission
import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoTeleop


class CrescendoUploadCacheDB(db: AppDatabase) {
    private val dbQuery = db.crescendoUploadCacheQueries

    fun getAll(): Map<Long, CrescendoSubmission> {
        return this.dbQuery.getAll().executeAsList().associate {
            it._id to
                    CrescendoSubmission(
                        brokeDown = it.brokeDown,
                        comments = it.comments,
                        competition = it.competition,
                        defensive = it.defensive,
                        matchNumber = it.matchNumber,
                        penaltyPointsEarned = it.penalty,
                        rankingPoints = it.rankingPoints,
                        ranking = CrescendoRankingPoints(
                            melody = it.melody,
                            ensemble = it.ensemble
                        ),
                        score = it.score,
                        stage = CrescendoStage(
                            state = try {
                                CrescendoStageState.valueOf(it.stageState)
                            } catch (_: Exception) {
                                CrescendoStageState.NOT_PARKED
                            },
                            harmony = it.harmony,
                            trap = it.trap
                        ),
                        teamNumber = it.teamNumber,
                        teleop = CrescendoTeleop(
                            ampNotes = it.teleopAmp,
                            speakerUnamped = it.teleopSpeakerUnamp,
                            speakerAmped = it.teleopSpeakerAmp
                        ),
                        tied = it.tie,
                        won = it.won,
                        auto = CrescendoAuto(
                            leave = it.autoLeave,
                            attempted = it.auto_attempted,
                            scored = it.auto_scored
                        ),
                        rating = it.rating,
                        human = CrescendoHuman(
                            source = it.human_source,
                            rating = it.human_skill
                        ),
                        defenseRating = it.defense_rating,
                        coopertition = it.coop
                    )
        }
    }

    fun insert(obj: CrescendoSubmission) {
        this.dbQuery.insert(
            auto_attempted = obj.auto.attempted,
            autoLeave = obj.auto.leave,
            auto_scored = obj.auto.scored,
            brokeDown = obj.brokeDown,
            comments = obj.comments ?: "",
            competition = obj.competition,
            defensive = obj.defensive,
            ensemble = obj.ranking.ensemble,
            harmony = obj.stage.harmony,
            matchNumber = obj.matchNumber,
            melody = obj.ranking.melody,
            penalty = obj.penaltyPointsEarned,
            rankingPoints = obj.rankingPoints,
            score = obj.score,
            stageState = obj.stage.state.name,
            teamNumber = obj.teamNumber,
            teleopAmp = obj.teleop.ampNotes,
            teleopSpeakerAmp = obj.teleop.speakerAmped,
            teleopSpeakerUnamp = obj.teleop.speakerUnamped,
            tie = obj.tied,
            trap = obj.stage.trap,
            won = obj.won,
            rating = obj.rating,
            human_source = obj.human.source,
            defense_rating = obj.defenseRating,
            coop = obj.coopertition,
            human_skill = obj.human.rating
        )
    }

    fun insert(objs: List<CrescendoSubmission>) {
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

    fun getOne(id: Long): CrescendoSubmission? {
        return this.dbQuery.get(id).executeAsOneOrNull()?.let {
            CrescendoSubmission(
                brokeDown = it.brokeDown,
                comments = it.comments,
                competition = it.competition,
                defensive = it.defensive,
                matchNumber = it.matchNumber,
                penaltyPointsEarned = it.penalty,
                rankingPoints = it.rankingPoints,
                ranking = CrescendoRankingPoints(
                    melody = it.melody,
                    ensemble = it.ensemble
                ),
                score = it.score,
                stage = CrescendoStage(
                    state = try {
                        CrescendoStageState.valueOf(it.stageState)
                    } catch (_: Exception) {
                        CrescendoStageState.NOT_PARKED
                    },
                    harmony = it.harmony,
                    trap = it.trap
                ),
                teamNumber = it.teamNumber,
                teleop = CrescendoTeleop(
                    ampNotes = it.teleopAmp,
                    speakerUnamped = it.teleopSpeakerUnamp,
                    speakerAmped = it.teleopSpeakerAmp
                ),
                tied = it.tie,
                won = it.won,
                auto = CrescendoAuto(
                    leave = it.autoLeave,
                    attempted = it.auto_attempted,
                    scored = it.auto_scored
                ),
                rating = it.rating,
                human = CrescendoHuman(
                    source = it.human_source,
                    rating = it.human_skill
                ),
                coopertition = it.coop,
                defenseRating = it.defense_rating
            )
        }
    }
}