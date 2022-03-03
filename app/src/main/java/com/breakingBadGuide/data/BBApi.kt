package com.breakingBadGuide.data

import com.breakingBadGuide.data.models.AllCharactersItem
import com.breakingBadGuide.data.models.CharacterByIdItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BBApi {

    @GET("characters")
    suspend fun getAllCharacters(): Response<List<AllCharactersItem>>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<List<CharacterByIdItem>>
}