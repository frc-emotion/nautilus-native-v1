package org.nautilusapp.localstorage

import app.cash.sqldelight.db.SqlDriver

//expect class DatabaseDriverFactory {
//    fun createDriver(): SqlDriver
//}

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}