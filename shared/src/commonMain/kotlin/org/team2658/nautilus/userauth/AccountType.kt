package org.team2658.nautilus.userauth

enum class AccountType(val value: Int) {
    SUPERUSER(4),
    ADMIN(3),
    LEAD(2),
    BASE(1),
    UNVERIFIED(0),;

    companion object {
        fun of(value: Int?): AccountType {
            return when(value) {
                in 4..Int.MAX_VALUE -> SUPERUSER
                3 -> ADMIN
                2 -> LEAD
                1 -> BASE
                else -> UNVERIFIED
            }
        }
    }
}