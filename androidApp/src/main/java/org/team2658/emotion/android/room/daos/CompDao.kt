package org.team2658.emotion.android.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.team2658.emotion.android.room.entities.Competition

@Dao
interface CompDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComps(comps: List<Competition>)

    @Query("SELECT * FROM competitions WHERE year = :year")
    fun getComps(year: String): List<Competition>
}