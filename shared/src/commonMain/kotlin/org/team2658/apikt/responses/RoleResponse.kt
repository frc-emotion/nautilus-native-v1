package org.team2658.apikt.responses

import kotlinx.serialization.Serializable

@Serializable
data class RoleResponse(
    val name: String,
    val permissions: RolePermissionsResponse
)
