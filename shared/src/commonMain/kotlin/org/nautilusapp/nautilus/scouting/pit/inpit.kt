package org.nautilusapp.nautilus.scouting.pit

import kotlinx.serialization.Serializable

@Serializable
data class Inpit(
    val teamNumber: Int,
    val data: Map<String, InpitData>
)

@Serializable
data class InpitData(
    val value: String,
    val lastUpdatedAt: Long,
    val lastUpdatedBy: String
)