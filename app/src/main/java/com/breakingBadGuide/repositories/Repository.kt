package com.breakingBadGuide.repositories

import com.breakingBadGuide.data.responses.AllCharactersResponse
import com.breakingBadGuide.data.responses.CharacterByIdResponse
import com.breakingBadGuide.utils.ResponseWrapper

interface Repository {

    suspend fun getAllCharacters(): ResponseWrapper<AllCharactersResponse>

    suspend fun getCharacterById(id: Int): ResponseWrapper<CharacterByIdResponse>
}