package kr.ac.kumoh.ce.s20190207.s23w15material

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

enum class SongScreen {
    SongList,
    SingerList
}

@Composable
fun SongDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SongDrawerSheet(drawerState)
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            topBar = {
                SongTopBar(drawerState)
            },
        ) { innerPadding ->
            Text("노래 내용", Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun SongDrawerSheet(
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            icon = { SongIcon() },
            label = { Text("노래 리스트") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        )
        NavigationDrawerItem(
            icon = { SingerIcon() },
            label = { Text("가수 리스트") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        )
    }
}

@Composable
fun SongIcon() {
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "노래"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongTopBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text("노래 앱")
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "메뉴 아이콘"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
fun SingerIcon() {
    Icon(
        imageVector = Icons.Default.Face,
        contentDescription = "가수"
    )
}