package com.example.tbctask15.network

import com.example.tbctask15.model.Login
import com.example.tbctask15.model.Register
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitObj {
    private const val BASE_URL = "https://reqres.in/api/"

    private val retrofitBuilder by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun getInfo(): Auth = retrofitBuilder.create(Auth::class.java)

}

interface Auth{
    @POST("login")
    suspend fun loginResp(@Body userData: Login.LoginMain): Response<Login>

    @POST("register")
    suspend fun registerResp(@Body userData: Register.RegisterMain): Response<Register>
}