package com.kmm.dicodingsecondsubmission.data.api

import com.kmm.dicodingsecondsubmission.data.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("/search/users")
    fun searchUser(@Query("q") query: String): Call<SearchResponse>

    @GET("/users/{username}")
    fun user(@Path("username") username: String): Call<UserResponse>

    @GET("/users/{username}/followers")
    fun userFollowers(@Path("username") username: String): Call<List<UserItem>>

    @GET("/users/{username}/following")
    fun userFollowings(@Path("username") username: String): Call<List<UserItem>>
}