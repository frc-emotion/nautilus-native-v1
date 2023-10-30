package org.team2658.emotion.android.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competitions")
data class Competition(
    @PrimaryKey() val name: String,
    val year: String,
)
