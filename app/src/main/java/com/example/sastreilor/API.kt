package com.example.sastreilor

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


interface EmailServices{
    @GET("check")
    suspend fun checkMail(@Query("access_key") key:String,
                          @Query("email") email:String
    ):EmailResponse
}
object API {
    const val API_KEY = BuildConfig.API_KEY
    private val BASE_URL = "https://api.apilayer.com/email_verification/"

    private val moshi = Moshi.Builder().add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()

    private val logging = HttpLoggingInterceptor()

    private val httpClient = OkHttpClient.Builder().apply {
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    private val retrofit = Retrofit.Builder().
            addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val service : EmailServices by lazy{ retrofit.create(EmailServices::class.java)}
}