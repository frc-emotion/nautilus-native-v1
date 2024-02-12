package org.nautilusapp.localstorage
import org.nautilusapp.network.models.Season

class SeasonsDB(db: AppDatabase) {
    private val dbQuery = db.seasonsQueries

    fun getAll(): List<Season> {
        return this.dbQuery.getAllSeasons().executeAsList().map {
            Season(
                year = it.year,
                name = it.name,
                competitions = it.competitions?.split(",") ?: listOf(),
                attendancePeriods = it.attendance_periods?.split(",") ?: listOf()
            )
        }
    }

    fun getCompsFromSeason(year: Int): List<String> {
        return this.dbQuery.getCompetitionsByYear(year).executeAsList().map { it.name }
    }

    fun getAttendancePeriods(): List<String> = this.dbQuery.getAttendancePeriods().executeAsList()

    fun insert(season: Season) {
        this.dbQuery.transaction{
            dbQuery.insertSeason(
                year = season.year,
                name = season.name,
            )
            season.competitions.forEach {
                dbQuery.insertCompetition(
                    name = it,
                    year = season.year,
                )
            }
            season.attendancePeriods.forEach {
                dbQuery.insertAttendancePeriod(
                    name = it,
                    year = season.year,
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