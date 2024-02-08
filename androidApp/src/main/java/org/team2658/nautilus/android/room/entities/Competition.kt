package org.team2658.nautilus.android.room.entities

import androidx.room.Entity

@Entity(tableName = "competitions", primaryKeys = ["name", "year"])
data class Competition(
    val name: String,
    val year: String,
)
