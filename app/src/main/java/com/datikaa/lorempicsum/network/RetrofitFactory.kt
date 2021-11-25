package com.datikaa.lorempicsum.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object RetrofitFactory {

    fun build(): PicsumService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}