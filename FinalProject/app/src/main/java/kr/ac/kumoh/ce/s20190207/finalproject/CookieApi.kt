package kr.ac.kumoh.ce.s20190207.finalproject

import retrofit2.http.GET
interface CookieApi {
    @GET("cookie")
    suspend fun getCookies(): List<Cookie>
}