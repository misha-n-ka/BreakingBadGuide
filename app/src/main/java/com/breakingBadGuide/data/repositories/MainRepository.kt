package com.breakingBadGuide.data.repositories

import com.breakingBadGuide.data.BBApi
import com.breakingBadGuide.data.ResponseWrapper
import com.breakingBadGuide.data.models.AllCharactersItem
import com.breakingBadGuide.data.models.CharacterByIdItem
import com.breakingBadGuide.domain.repositories.Repository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class MainRepository @Inject constructor(
    private val api: BBApi
) : Repository {

    override suspend fun getAllCharacters(): ResponseWrapper<List<AllCharactersItem>> {
        return try {
            val response = api.getAllCharacters()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                ResponseWrapper.Success(result)
            } else {
                ResponseWrapper.Error(response.message() ?: "Get all character Network Error")
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ResponseWrapper.Error(e.message ?: "Get all characters Exception")
        }
    }

    override suspend fun getCharacterById(id: Int): ResponseWrapper<List<CharacterByIdItem>> {
        return try {
            val response = api.getCharacterById(id)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                ResponseWrapper.Success(result)
            } else {
                ResponseWrapper.Error(response.message() ?: "get character by Id Network Error")
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ResponseWrapper.Error(e.message ?: "Get character by Id Exception")
        }
    }
}