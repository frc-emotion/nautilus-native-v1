package org.team2658.localstorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team2658.network.models.Season

class SeasonsDB(db: AppDatabase) {
    private val dbQuery = db.seasonsQueries

    fun getAll(): List<Season> {
        return this.dbQuery.getAllSeasons().executeAsList().map {
            Season(
                _id = it.id,
                year = it.year,
                name = it.name,
                competitions = it.competitions?.split(",") ?: listOf(),
                attendancePeriods = it.attendance_periods_JSON?.let{str -> Json.decodeFromString<List<String>>(str)}
            )
        }
    }

//    fun getLatestComps(): List<Pair<String, Int>> {
//        return this.dbQuery.getCompetitionsFromLatestSeason().executeAsList().map {
//            Pair(it.name, it.year)
//        }
//    }

    fun getCompsFromSeason(year: Int): List<String> {
        return this.dbQuery.getCompetitionsByYear(year).executeAsList().map { it.name }
    }

    fun getAttendancePeriods(): Map<Int, List<String>> { //map of year to attendancePeriod[]
        val res =  this.dbQuery.getAttendancePeriods().executeAsList()
        return res.associate { it.year to Json.decodeFromString(it.attendance_periods_JSON) }
    }

    fun insert(season: Season) {
        this.dbQuery.transaction{
            dbQuery.insertSeason(
                id = season._id,
                year = season.year,
                name = season.name,
                attendance_periods_JSON = season.attendancePeriods?.let{Json.encodeToString(season.attendancePeriods)}
            )
            season.competitions.forEach {
                dbQuery.insertCompetition(
                    name = it,
                    year = season.year,
                    season = season._id
                )
            }
        }
    }

    fun insert(seasons: List<Season>) {
        seasons.forEach { this.insert(it) }
    }

    fun deleteAll() {
        this.dbQuery.deleteAllSeasons()
    }

}