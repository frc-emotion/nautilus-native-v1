package org.team2658.emotion.android.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.team2658.emotion.android.room.entities.MeetingEntity

@Dao
interface MeetingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeetings(data: List<MeetingEntity>)

    @Delete
    suspend fun deleteMeetings(data: List<MeetingEntity>): Int

    @Query("SELECT * FROM meetings WHERE startTime <= :currentTime AND endTime >= :currentTime ORDER BY startTime DESC")
    fun getCurrent(currentTime: Long): List<MeetingEntity>

    @Query("SELECT * FROM meetings WHERE endTime <= :currentTime")
    fun getOutdated(currentTime: Long): List<MeetingEntity>

    @Query("SELECT * FROM meetings")
    fun getAll(): List<MeetingEntity>

    @Query("SELECT * FROM meetings WHERE id = :id LIMIT 1")
    fun getOne(id: String): MeetingEntity?
}