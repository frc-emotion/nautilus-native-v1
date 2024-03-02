package org.nautilusapp.nautilus.roles

import kotlinx.serialization.Serializable
import org.nautilusapp.nautilus.userauth.UserPermissions

@Serializable
data class UserRole(
    val name: String,
    val _id: String,
    val permissions: UserPermissions
)
