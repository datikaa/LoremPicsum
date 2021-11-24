package com.datikaa.lorempicsum.network

import com.datikaa.lorempicsum.network.response.PicsumResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumService {

    @GET("v2/list")
    suspend fun list(): List<PicsumResponseItem>

    @GET("v2/list")
    suspend fun list(@Query("page") page: Int, @Query("limit") limit: Int): List<PicsumResponseItem>
}