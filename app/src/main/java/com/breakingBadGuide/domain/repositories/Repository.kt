package com.breakingBadGuide.domain.repositories

import com.breakingBadGuide.data.ResponseWrapper
import com.breakingBadGuide.data.models.AllCharactersItem
import com.breakingBadGuide.data.models.CharacterByIdItem

interface Repository {

    suspend fun getAllCharacters(): ResponseWrapper<List<AllCharactersItem>>

    suspend fun getCharacterById(id: Int): ResponseWrapper<List<CharacterByIdItem>>
}