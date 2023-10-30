package org.team2658.emotion.android.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competitions", primaryKeys = ["name", "year"])
data class Competition(
    val name: String,
    val year: String,
)
