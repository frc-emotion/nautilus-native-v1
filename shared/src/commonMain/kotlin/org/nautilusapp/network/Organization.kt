package org.nautilusapp.network

import kotlinx.serialization.Serializable

@Serializable
data class Organization(val name: String, val url: String)
