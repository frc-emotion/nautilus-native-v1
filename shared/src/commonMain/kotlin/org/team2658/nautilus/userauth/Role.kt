package org.team2658.nautilus.userauth

import org.team2658.network.models.RoleModel

data class Role(
    val name: String,
    val permissions: UserPermissions
) {
    fun toSerializeable(): RoleModel {
        return RoleModel(
            name = name,
            permissions = permissions.toSerializeable()
        )
    }

    companion object {
        fun fromSerializeable(role: RoleModel): Role {
            return Role(
                name = role.name,
                permissions = UserPermissions.fromSerializeable(role.permissions)
            )
        }
    }
}
