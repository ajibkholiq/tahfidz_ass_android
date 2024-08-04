package com.treedevs.tahfidz.networks

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiInterface{

            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            //TODO : Sesuaikan dengan base URL yang telah dibuat sebelumnya
            val retrofit = Retrofit.Builder()
                .baseUrl("https://e-tahfidz.ajibkholiq.my.id/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
        fun getApiOpenAI(): ApiInterface{

            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            //TODO : Sesuaikan dengan base URL yang telah dibuat sebelumnya
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openai.com/v1/audio/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}