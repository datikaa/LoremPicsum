package com.datikaa.lorempicsum.network

import com.datikaa.lorempicsum.data.ListItem
import retrofit2.http.GET

interface PicsumService {

    @GET("v2/list")
    suspend fun list(): List<ListItem>
}