package com.example.codrum.IO

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
    private val retrofit = Retrofit.Builder()
        .baseUrl("15.165.148.101:443")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService():IRetrofit{
        return retrofit.create(IRetrofit::class.java)
    }
}