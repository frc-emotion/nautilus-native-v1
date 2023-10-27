package org.team2658.emotion.android.room.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import org.team2658.emotion.android.room.daos.ChargedUpDao
import org.team2658.emotion.android.room.entities.ChargedUpEntity

@Database(
    entities = [ChargedUpEntity::class],
    version = 1
)
abstract class ScoutingDB: RoomDatabase() {
    abstract val chargedUpDao: ChargedUpDao
}