package org.team2658.emotion.android.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.team2658.emotion.android.room.entities.ChargedUpEntity

@Dao
interface ChargedUpDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChargedUp(data: ChargedUpEntity)

    @Delete
    suspend fun deleteChargedUp(data: ChargedUpEntity): Int

    @Query("SELECT * FROM chargedups")
    fun getChargedUps(): List<ChargedUpEntity>
}