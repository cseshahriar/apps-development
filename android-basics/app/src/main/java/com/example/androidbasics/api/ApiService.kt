package com.example.androidbasics.api

import retrofit2.Call
import retrofit2.http.GET

// Model Class (represents one item from the API)
data class UserCategory(
    val id: Int,
    val name: String
)

interface ApiService {
    @GET("users")   // endpoint after the baseUrl
    fun getUsers(): Call<List<UserCategory>>
}
