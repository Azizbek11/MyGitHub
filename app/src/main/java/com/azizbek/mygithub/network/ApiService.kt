package com.azizbek.mygithub.network

import com.azizbek.mygithub.model.PopularRepo
import com.azizbek.mygithub.model.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    fun getPopularRepo(@Query("q") language : String,
                       @Query("sort") sort:String): Call<PopularRepo>


    @GET("search/repositories")
    fun getSearchResult(@Query("q") language : String,
                        @Query("sort") sort:String): Call<PopularRepo>

    @GET("users/{user}")
    fun getUserData(@Path("user") userName:String):Call<UserInfo>
}
