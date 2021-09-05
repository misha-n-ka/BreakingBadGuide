package com.breakingBadGuide.repositories

import com.breakingBadGuide.data.BBApi
import com.breakingBadGuide.data.responses.AllCharactersResponse
import com.breakingBadGuide.data.responses.CharacterByIdResponse
import com.breakingBadGuide.utils.ResponseWrapper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: BBApi
) : Repository {


    // function for gets an array of all characters
    // and returns Wrapper of Success or Error response
    override suspend fun getAllCharacters(): ResponseWrapper<AllCharactersResponse> {
        return try {
            val response = api.getAllCharacters()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                ResponseWrapper.Success(result)
            } else {
                ResponseWrapper.Error(response.message() ?: "Undefined Error")
            }
        } catch (e: Exception) {
            ResponseWrapper.Error(e.message ?: "Error is happened")
        }
    }

    // function for gets an array of one character by ID
    // and returns Wrapper of Success or Error response
    override suspend fun getCharacterById(id: Int): ResponseWrapper<CharacterByIdResponse> {
        return try {
            val response = api.getCharacterById(id)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                ResponseWrapper.Success(result)
            } else {
                ResponseWrapper.Error(response.message() ?: "Undefined Error")
            }
        } catch (e: Exception) {
            ResponseWrapper.Error(e.message ?: "Error is happened")
        }
    }
}