package kr.ac.kumoh.ce.s20190207.finalproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CookieViewModel(): ViewModel() {
    private val SERVER_URL = "https://port-0-backend-1drvf2llomgp0jt.sel5.cloudtype.app/"
    private val cookieApi: CookieApi
    private val _cookieList = MutableLiveData<List<Cookie>>()
    val cookieList: LiveData<List<Cookie>>
        get() = _cookieList

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        cookieApi = retrofit.create(CookieApi::class.java)
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch{
            try{
                val response = cookieApi.getCookies()
                _cookieList.value = response
            }catch(e: Exception){
                Log.e("fetchData()", e.toString())
            }
        }
    }
}