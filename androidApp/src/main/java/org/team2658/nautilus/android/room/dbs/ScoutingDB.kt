package org.team2658.nautilus.android.room.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import org.team2658.nautilus.android.room.daos.ChargedUpDao
import org.team2658.nautilus.android.room.daos.CompDao
import org.team2658.nautilus.android.room.entities.ChargedUpEntity
import org.team2658.nautilus.android.room.entities.Competition

@Database(
    entities = [ChargedUpEntity::class, Competition::class],
    version = 5,
)
abstract class ScoutingDB: RoomDatabase() {
    abstract val chargedUpDao: ChargedUpDao
    abstract val compsDao: CompDao
}