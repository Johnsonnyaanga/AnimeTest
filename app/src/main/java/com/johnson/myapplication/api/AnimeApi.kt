package com.johnson.myapplication.api

import com.johnson.myapplication.data.AnimeDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



    interface AnimeApi {
        @GET("top/anime")
        suspend fun getTopAnimes(): Response<AnimeDataResponse>



    }