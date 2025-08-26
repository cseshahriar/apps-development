package com.example.androidbasics.network

import com.example.androidbasics.model.PhotoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("photos")
    fun uploadPhoto(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody
    ): Call<PhotoResponse>
}
