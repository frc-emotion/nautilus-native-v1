package org.team2658.nautilus.userauth

enum class AuthState {
    LOGGED_IN,
    NOT_LOGGED_IN,
    AWAITING_VERIFICATION
}

fun authState(user: TokenUser?): AuthState {
    return when(user?.accountType) {
        AccountType.BASE, AccountType.ADMIN, AccountType.LEAD, AccountType.SUPERUSER -> AuthState.LOGGED_IN
        AccountType.UNVERIFIED -> AuthState.AWAITING_VERIFICATION
        null -> AuthState.NOT_LOGGED_IN
    }
}