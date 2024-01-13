package org.team2658.localstorage

class CompsDB(db: AppDatabase) {
    private val dbQuery = db.competitionsQueries

    fun getAll(): List<Competition> {
        return this.dbQuery.getAll().executeAsList()
    }

    fun insert(obj: Competition) {
        this.dbQuery.insert(
            name = obj.name,
            year = obj.year
        )
    }

    fun insert(objs: List<Competition>) {
        objs.forEach { this.insert(it) }
    }

    fun deleteAll() {
        this.dbQuery.deleteAll()
    }
}