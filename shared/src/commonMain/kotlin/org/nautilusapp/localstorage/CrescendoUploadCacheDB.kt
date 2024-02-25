package org.nautilusapp.localstorage

import org.nautilusapp.nautilus.scouting.scoutingdata.CrescendoAuto
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
                            trapNotes = it.trapNotes
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
                            ampNotes = it.autoAmp,
                            speakerNotes = it.autoSpeaker
                        )
                    )
        }
    }

    fun insert(obj: CrescendoSubmission) {
        this.dbQuery.insert(
            autoAmp = obj.auto.ampNotes,
            autoLeave = obj.auto.leave,
            autoSpeaker = obj.auto.speakerNotes,
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
            trapNotes = obj.stage.trapNotes,
            won = obj.won
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
                    trapNotes = it.trapNotes
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
                    ampNotes = it.autoAmp,
                    speakerNotes = it.autoSpeaker
                )
            )
        }
    }
}