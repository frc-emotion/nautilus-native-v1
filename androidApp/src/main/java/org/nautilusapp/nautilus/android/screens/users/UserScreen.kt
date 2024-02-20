package org.nautilusapp.nautilus.android.screens.users

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import org.nautilusapp.nautilus.android.ui.navigation.NestedScaffold
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.userauth.AccountType
import org.nautilusapp.nautilus.userauth.PartialUser
import org.nautilusapp.nautilus.userauth.Subteam
import org.nautilusapp.nautilus.userauth.User

@Composable
fun UserList(
    loadUsers: suspend () -> DataResult<List<User.WithoutToken>>,
    initialUsers: List<User.WithoutToken> = emptyList()
) {
    var users by remember { mutableStateOf(initialUsers) }
    LaunchedEffect(true) {
        val result = loadUsers()
        if (result is Result.Success) {
            users = result.data
        }
    }
    LazyScreen(
        onRefresh = {
            loadUsers().also {
                if (it is Result.Success) {
                    users = it.data
                }
            }
        })
    {
        items(users, key = { it._id }) {
            UserDetailCard(user = it)
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun UsersScreen(
    dataHandler: DataHandler
) {
    val initUsers = dataHandler.users.loadAll()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(apiLevel = 33)
@Composable
fun UserListPreview() {
    val users = (1..40).map {
        PartialUser(
            _id = it.toString(),
            username = "user_$it",
            email = "user$it@meow.meow",
            accountType = AccountType.BASE,
            firstname = "User",
            lastname = "McUserface",
            roles = emptyList(),
            subteam = Subteam.SOFTWARE
        )
    }
    val snack = remember { SnackbarHostState() }
    MainTheme(preference = ColorTheme.NAUTILUS_MIDNIGHT) {
        NestedScaffold(snack, topBar = {
            TopAppBar(title = {
                Text("Users")
            })
        }) {
            UserList(loadUsers = { Result.Success(users) })
        }
    }
}