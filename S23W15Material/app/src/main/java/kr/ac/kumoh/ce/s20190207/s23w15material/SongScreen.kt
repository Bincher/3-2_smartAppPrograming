package kr.ac.kumoh.ce.s20190207.s23w15material

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

enum class SongScreen {
    SongList,
    SingerList
}

@Composable
fun SongDrawer() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SongDrawerSheet(drawerState) {
                navController.navigate(it)
            }
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            topBar = {
                SongTopBar(drawerState)
            },
            bottomBar = {
                SongBottomNavigation {
                    navController.navigate(it)
                }
            },
        ) { innerPadding ->
            //SongList(innerPadding)
            //Text("노래 내용", Modifier.padding(innerPadding))
            //Modifier.padding(innerPadding)이 없으면 TopBar에 가려짐
            //innerPadding이 없으면 SongList(it)
            NavHost(
                navController,
                startDestination = SongScreen.SongList.name
            ) {
                composable(SongScreen.SongList.name) {
                    SongList(innerPadding)
                }
                composable(SongScreen.SingerList.name) {
                    SingerList(innerPadding)
                }
            }
        }
    }
}

@Composable
fun SongDrawerSheet(
    drawerState: DrawerState,
    onNavigateToList: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            icon = { SongIcon() },
            label = { Text("노래 리스트") },
            selected = false,
            onClick = {
                onNavigateToList(SongScreen.SongList.name)
                scope.launch {
                    drawerState.close()
                }
            }
        )
        // 시험문제내면 다 틀리겠는데
        NavigationDrawerItem(
            icon = { SingerIcon() },
            label = { Text("가수 리스트") },
            selected = false,
            onClick = {
                onNavigateToList(SongScreen.SingerList.name)
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

@Composable
fun SongBottomNavigation(onNavigateToList: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        NavigationBarItem(
            icon = { SongIcon() },
            label = {
                Text("노래")
            },
            selected = false,
            onClick = {
                onNavigateToList(SongScreen.SongList.name)
            }
        )
        NavigationBarItem(
            icon = { SingerIcon() },
            label = {
                Text("가수")
            },
            selected = false,
            onClick = {
                onNavigateToList(SongScreen.SingerList.name)
            }
        )
    }
}

@Composable
fun SongList(padding: PaddingValues) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = padding,
    ) {
        items(50) {
            SongItem(it)
        }
    }
}

@Composable
fun SongItem(index: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Text(
            "노래 $index",
            Modifier.padding(30.dp),
            fontSize = 30.sp
        )
    }
}

@Composable
fun SingerList(padding: PaddingValues) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = padding
    ) {
        items(50) {
            SingerItem(it)
        }
    }
}

@Composable
fun SingerItem(index: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Text(
            "가수 $index",
            Modifier.padding(30.dp),
            fontSize = 30.sp
        )
    }
}