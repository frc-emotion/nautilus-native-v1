package org.team2658.nautilus.userauth

data class Employer(
    val name: String,
    val logo: String?, //URL to CDN
    val doesMatch: Boolean,
    val hasGrant: Boolean,
    val grantURL: String?,
)
