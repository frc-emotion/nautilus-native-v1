package org.team2658.nautilus.android.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.team2658.nautilus.android.room.entities.ChargedUpEntity

@Dao
interface ChargedUpDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChargedUp(data: ChargedUpEntity)

    @Delete
    suspend fun deleteChargedUp(data: ChargedUpEntity): Int

    @Delete
    suspend fun deleteChargedUps(data: List<ChargedUpEntity>): Int

    @Query("SELECT * FROM chargedups WHERE storageType = 'TEMP'")
    fun getChargedUpTemp(): List<ChargedUpEntity>

    @Query("SELECT * FROM chargedups")
    fun getAllChargedUps(): List<ChargedUpEntity>
}