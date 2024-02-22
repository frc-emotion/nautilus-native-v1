package org.nautilusapp.nautilus.android.screens.users

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.DataHandler
import org.nautilusapp.nautilus.DataResult
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.MainTheme
import org.nautilusapp.nautilus.android.ui.composables.UserDetailCard
import org.nautilusapp.nautilus.android.ui.composables.containers.LazyScreen
import org.nautilusapp.nautilus.android.ui.composables.displayName
import org.nautilusapp.nautilus.android.ui.navigation.NestedScaffold
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.userauth.AccountType
import org.nautilusapp.nautilus.userauth.FullUser
import org.nautilusapp.nautilus.userauth.PartialUser
import org.nautilusapp.nautilus.userauth.Subteam
import org.nautilusapp.nautilus.userauth.User
import org.nautilusapp.nautilus.userauth.UserPermissions
import org.nautilusapp.nautilus.userauth.isLead

@Composable
fun UserList(
    loadUsers: suspend () -> DataResult<List<User.WithoutToken>>,
    users: List<User.WithoutToken> = emptyList(),
    snack: SnackbarHostState? = null
) {
    val sectioned = users.sectionBySubteam()
    LazyScreen(
        onRefresh = {
            loadUsers()
        },
        snack = snack,
        contentPadding = PaddingValues(horizontal = 8.dp)
    )
    {
//        items(users, key = { it._id }) {
//            UserDetailCard(user = it)
//            Spacer(modifier = Modifier.size(8.dp))
//        }
        sectioned.forEach { (subteam, list) ->
            item(key = subteam) {
                Text(subteam.displayName)
                Spacer(modifier = Modifier.size(8.dp))
            }
            val (lead, rest) = list.partition { it.isLead }
            items(lead.sortedBy {
                it.lastname
            }, key = { it._id }) {
                UserDetailCard(user = it)
                Spacer(modifier = Modifier.size(8.dp))
            }
            items(rest.sortedBy {
                it.lastname
            }, key = { it._id }) {
                UserDetailCard(user = it)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    dataHandler: DataHandler,
    snack: SnackbarHostState
) {
    val initUsers = dataHandler.users.loadAll()
    var users by remember { mutableStateOf(initUsers) }
    suspend fun loadUsers() = dataHandler.users.sync().userList.also {
        if (it is Result.Success) {
            users = it.data
        }
    }
    NestedScaffold(snack = snack, topBar = {
        TopAppBar(title = {
            Text("Users")
        })
    }) {
        UserList(loadUsers = ::loadUsers, users = initUsers, snack = snack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(apiLevel = 33)
@Composable
fun UserListPreview() {
    val users = (1..40).map {
        exampleUser(it, false)
    }
    val snack = remember { SnackbarHostState() }
    MainTheme(preference = ColorTheme.NAUTILUS_MIDNIGHT) {
        NestedScaffold(snack, topBar = {
            TopAppBar(title = {
                Text("Users")
            })
        }) {
            UserList(loadUsers = { Result.Success(users) }, users = users)
        }
    }
}

fun exampleUser(index: Int, full: Boolean = false): User.WithoutToken {
    if (full) {
        return FullUser(
            _id = index.toString(),
            username = "user_$index",
            email = "user$index@meow.meow",
            accountType = AccountType.BASE,
            firstname = "User",
            lastname = "McUserface",
            roles = emptyList(),
            subteam = Subteam.SOFTWARE,
            grade = 12,
            accountUpdateVersion = 0,
            attendance = emptyMap(),
            permissions = UserPermissions(),
            phone = "123-456-7890",
        )
    }

    return PartialUser(
        _id = index.toString(),
        username = "user_$index",
        email = "user$index@meow.meow",
        accountType = AccountType.BASE,
        firstname = "User",
        lastname = "McUserface",
        roles = emptyList(),
        subteam = Subteam.SOFTWARE
    )
}