package kr.ac.kumoh.ce.s20190207.s23w12retrofit

import retrofit2.http.GET

interface SongApi {
    @GET("song")
    suspend fun getSongs(): List<Song>
}