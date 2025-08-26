package com.example.androidbasics.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

// Model Class (represents one item from the API)
data class UserCategory(
    val id: Int,
    val name: String
)

interface ApiService {
    // users get api
    @GET("users")   // endpoint after the baseUrl
    fun getUsers(): Call<List<UserCategory>>
}



