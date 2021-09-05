package com.breakingBadGuide.data

import com.breakingBadGuide.data.responses.AllCharactersResponse
import com.breakingBadGuide.data.responses.CharacterByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BBApi {

    @GET("characters")
    suspend fun getAllCharacters(): Response<AllCharactersResponse>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<CharacterByIdResponse>
}