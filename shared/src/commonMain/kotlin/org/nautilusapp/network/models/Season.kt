package org.nautilusapp.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Season(
    val year: Int,
    val name: String,
    val competitions: List<String>,
    val attendancePeriods: List<String>
)
