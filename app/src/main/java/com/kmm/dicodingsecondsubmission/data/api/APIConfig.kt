package com.kmm.dicodingsecondsubmission.data.api

import com.kmm.dicodingsecondsubmission.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIConfig {
    companion object{
        private const val AUTHORIZATION = "Authorization"
        fun getApiService(): APIService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val newReq  = it.request().newBuilder().addHeader(AUTHORIZATION,"Bearer ${BuildConfig.API_KEY}").build()
                    it.proceed(newReq)
                }
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}