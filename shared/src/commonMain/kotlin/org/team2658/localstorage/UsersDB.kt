package org.team2658.localstorage

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team2658.emotion.userauth.Subteam
import org.team2658.emotion.userauth.User

typealias UserIDInfo = GetInfoById

class UsersDB(db: AppDatabase) {
    private val users = db.usersQueries

    fun insertUser(user: User) {
        users.insert(
            id = user._id,
            firstname = user.firstName,
            lastname = user.lastName,
            username = user.username,
            email = user.email,
            phone = user.phoneNumber,
            token = user.token,
            subteam = user.subteam.name,
            grade = user.grade,
            accountType = user.accountType.value,
            rolesJSON = Json.encodeToString(user.roles),
            attendanceJSON = Json.encodeToString(user.attendance),
            isLoggedInUser = false
        )
    }

    fun insertUsers(users: List<User>) {
        users.forEach { insertUser(it) }
    }

    fun identifyUser(oid: String): UserIDInfo? {
        return try {
            users.getInfoById(oid).executeAsOneOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getUser(oid: String): User? {
        return try {
            users.getById(oid).executeAsOneOrNull()?.let {
                User(
                    firstName = it.firstname,
                    lastName = it.lastname,
                    username = it.username,
                    email = it.email,
                    phoneNumber = it.phone,
                    token = it.token,
                    subteam = try {
                        Subteam.valueOf(it.subteam.trim().uppercase())
                    } catch (_: Exception) {
                        Subteam.NONE
                    },
                    grade = it.grade,
                    roles = Json.decodeFromString(it.rolesJSON),
                    accountType = org.team2658.emotion.userauth.AccountType.of(it.accountType),
                    attendance = Json.decodeFromString(it.attendanceJSON),
                    _id = it.id
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getUserByUsername(username: String): User? {
        return try {
            users.getByUsername(username).executeAsOneOrNull()?.let {
                User(
                    firstName = it.firstname,
                    lastName = it.lastname,
                    username = it.username,
                    email = it.email,
                    phoneNumber = it.phone,
                    token = it.token,
                    subteam = try {
                        Subteam.valueOf(it.subteam.trim().uppercase())
                    } catch (_: Exception) {
                        Subteam.NONE
                    },
                    grade = it.grade,
                    roles = Json.decodeFromString(it.rolesJSON),
                    accountType = org.team2658.emotion.userauth.AccountType.of(it.accountType),
                    attendance = Json.decodeFromString(it.attendanceJSON),
                    _id = it.id
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getUserByEmail(email: String): User? {
        return try {
            users.getByEmail(email).executeAsOneOrNull()?.let {
                User(
                    firstName = it.firstname,
                    lastName = it.lastname,
                    username = it.username,
                    email = it.email,
                    phoneNumber = it.phone,
                    token = it.token,
                    subteam = try {
                        Subteam.valueOf(it.subteam.trim().uppercase())
                    } catch (_: Exception) {
                        Subteam.NONE
                    },
                    grade = it.grade,
                    roles = Json.decodeFromString(it.rolesJSON),
                    accountType = org.team2658.emotion.userauth.AccountType.of(it.accountType),
                    attendance = Json.decodeFromString(it.attendanceJSON),
                    _id = it.id
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getUsers(): List<User>? {
        return try {
            users.getNotLoggedIn().executeAsList().map {
                User(
                    firstName = it.firstname,
                    lastName = it.lastname,
                    username = it.username,
                    email = it.email,
                    phoneNumber = it.phone,
                    token = it.token,
                    subteam = try {
                        Subteam.valueOf(it.subteam.trim().uppercase())
                    } catch (_: Exception) {
                        Subteam.NONE
                    },
                    grade = it.grade,
                    roles = Json.decodeFromString(it.rolesJSON),
                    accountType = org.team2658.emotion.userauth.AccountType.of(it.accountType),
                    attendance = Json.decodeFromString(it.attendanceJSON),
                    _id = it.id
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getLoggedInUser(): User? {
        return try {
            users.getLoggedInUser().executeAsOneOrNull()?.let {
                User(
                    firstName = it.firstname,
                    lastName = it.lastname,
                    username = it.username,
                    email = it.email,
                    phoneNumber = it.phone,
                    token = it.token,
                    subteam = try {
                        Subteam.valueOf(it.subteam.trim().uppercase())
                    } catch (_: Exception) {
                        Subteam.NONE
                    },
                    grade = it.grade,
                    roles = Json.decodeFromString(it.rolesJSON),
                    accountType = org.team2658.emotion.userauth.AccountType.of(it.accountType),
                    attendance = Json.decodeFromString(it.attendanceJSON),
                    _id = it.id
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun updateLoggedInUser(user: User): User? {
        return try {
        users.transaction {
            users.deleteLoggedIn()
            users.insert(
                id = user._id,
                firstname = user.firstName,
                lastname = user.lastName,
                username = user.username,
                email = user.email,
                phone = user.phoneNumber,
                token = user.token,
                subteam = user.subteam.name,
                grade = user.grade,
                accountType = user.accountType.value,
                rolesJSON = Json.encodeToString(user.roles),
                attendanceJSON = Json.encodeToString(user.attendance),
                isLoggedInUser = true
            )
        }
        getLoggedInUser()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteUser(user: User) {
        users.deleteOne(user._id)
    }

    fun clearUsers() {
        users.deleteNotLoggedIn()
    }

    fun logoutUser() {
        users.deleteLoggedIn()
    }
}