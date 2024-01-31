package org.team2658.localstorage

import app.cash.sqldelight.db.SqlDriver

//expect class DatabaseDriverFactory {
//    fun createDriver(): SqlDriver
//}

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}