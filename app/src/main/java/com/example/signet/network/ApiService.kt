package com.example.signet.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = "https://api.urlmeta.org"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @Headers("Authorization: Y2FyZS5uZXRzaXBzQGdtYWlsLmNvbTpZVEdwM0VsT2RDOVdpSkpMOEt5Yg==")
    @GET("/")
    suspend fun getMetaData(@Query("url") url: String): LinkMetaData

}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
