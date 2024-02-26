package org.nautilusapp.nautilus.android.screens.users

import org.nautilusapp.nautilus.userauth.Subteam
import org.nautilusapp.nautilus.userauth.User

/**
 * Take a list of users and return a map of sub-lists sectioned by subteam
 */
fun List<User.WithoutToken>.sectionBySubteam(): Map<Subteam, List<User.WithoutToken>> {
    return this.groupBy { it.subteam ?: Subteam.NONE }
}