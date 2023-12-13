package kr.ac.kumoh.ce.s20190207.s23w15material

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import kr.ac.kumoh.ce.s20190207.s23w15material.ui.theme.S23W15MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    S23W15MaterialTheme {
        SongDrawer()
    }
}