package kr.ac.kumoh.ce.s20190207.finalproject

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

enum class CookieScreen {
    List,
    Detail
}

@Composable
fun CookieApp(cookieList: List<Cookie>){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CookieScreen.List.name,
    ){
        composable(route = CookieScreen.List.name){
            CookieList(cookieList){
                navController.navigate(it)
            }
        }
        composable(
            route = CookieScreen.Detail.name+"/{index}",
            arguments = listOf(navArgument("index"){
              type = NavType.IntType
            })
        ){
            val index = it.arguments?.getInt("index") ?: -1
            if(index >= 0)
                CookieDetail(cookieList[index])
            }
        }
    }

@Composable
fun CookieList(list: List<Cookie>, onNavigateToDetail:(String)->Unit){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ){
        items(list.size){
            CookieItem(it, list[it], onNavigateToDetail)
        }
    }
}

@Composable
fun CookieItem(index: Int,
               cookie: Cookie,
               onNavigateToDetail: (String) -> Unit
){
    //var expanded by remember { mutableStateOf(false) }
    Card(
        //modifier = Modifier.clickable { expanded = !expanded },
        modifier = Modifier.clickable{
            onNavigateToDetail(CookieScreen.Detail.name + "/$index")
        },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                //.background(Color(255, 210, 210))
                .padding(8.dp)
        ) {
            AsyncImage(
                model = cookie.img,
                contentDescription = "쿠키 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                ImageRarity(cookie.Rarity_img)
                TextName(cookie.name)
                ImageInformation(cookie.Type_img, cookie.Location_img, cookie.element)
            }
        }
    }

}

@Composable
fun ImageRarity(rarity: String, modifier: Modifier = Modifier){
    Image(
        painter = rememberAsyncImagePainter(model = rarity),
        contentDescription = "쿠키 등급",
        modifier = modifier
            .size(width = 100.dp, height = 30.dp)
            .padding(2.dp)
    )
}
@Composable
fun TextName(name: String){
    val customFont = FontFamily(Font(R.font.cookierun_regular))
    val textStyle = LocalTextStyle.current.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        fontFamily = customFont
    )

    Text(name,
        style = textStyle,
        fontSize = 30.sp
    )
}

@Composable
fun ImageInformation(type: String, location: String, element: String){
    val context = LocalContext.current
    val customFont = FontFamily(Font(R.font.cookierun_regular))
    val textStyle = LocalTextStyle.current.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        fontFamily = customFont
    )

    Row(
        modifier = Modifier
            .padding(2.dp)
    ){
        AsyncImage(
            model = type,
            contentDescription = "쿠키 타입",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(30.dp)
                .padding(2.dp),
        )
        AsyncImage(
            model = location,
            contentDescription = "쿠키 위치",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(30.dp)
                .padding(2.dp),
        )
        Text(element,
            modifier = Modifier.padding(2.dp).size(30.dp),
            softWrap = false,
            style = textStyle
        )
    }

}

@Composable
fun CookieDetail(cookie: Cookie){
    val context = LocalContext.current
    val customFont = FontFamily(Font(R.font.cookierun_regular))
    val textStyle = LocalTextStyle.current.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        fontFamily = customFont
    )

    Column (
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            cookie.name,
            style = textStyle,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = cookie.img,
            contentDescription = "쿠키 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp),
            )

        Spacer(modifier = Modifier.height(16.dp))
        cookie.skill?.let {
            Text(
                it,
                style = textStyle,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://namu.wiki/w/${cookie.name}")
            )
            startActivity(context, intent, null)
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text("나무위키 검색", fontSize = 30.sp)
            }
        }
    }
}