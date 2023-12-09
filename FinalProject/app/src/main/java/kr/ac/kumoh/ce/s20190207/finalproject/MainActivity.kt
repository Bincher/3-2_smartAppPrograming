package kr.ac.kumoh.ce.s20190207.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ac.kumoh.ce.s20190207.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {
    private val viewModel: CookieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: CookieViewModel){
    val cookieList by viewModel.cookieList.observeAsState(emptyList())
    FinalProjectTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CookieList(cookieList)
        }
    }
}

@Composable
fun CookieList(list: List<Cookie>){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(list){ cookie->
            CookieItem(cookie)
        }
    }
}

@Composable
fun CookieItem(cookie: Cookie){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffffffcc))
            .padding(16.dp)
    ){
        TextName(cookie.name)
        TextType(cookie.Type_name)
    }
}

@Composable
fun TextName(name: String){
    Text(name, fontSize = 30.sp)
}

@Composable
fun TextType(type: String){
    Text(type, fontSize = 20.sp)
}