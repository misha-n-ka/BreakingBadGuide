package com.breakingBadGuide.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breakingBadGuide.data.responses.AllCharactersResponse
import com.breakingBadGuide.repositories.Repository
import com.breakingBadGuide.utils.ResponseWrapper
import com.breakingBadGuide.utils.StateWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    // StateFlow contains current fragment state
    private val _state = MutableStateFlow<StateWrapper>(StateWrapper.Empty)
    val state: StateFlow<StateWrapper> = _state

    fun getAllCharacters() {
        // Change fragment state to Loading
        _state.value = StateWrapper.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val responseWrapper: ResponseWrapper<AllCharactersResponse> =
                repository.getAllCharacters()
            ) {
                // if network request is succeed
                is ResponseWrapper.Success -> {
                    val allCharsList = responseWrapper.data
                    _state.value =
                        StateWrapper.Success(allCharsList!!)
                }
                // if network request has error
                is ResponseWrapper.Error ->
                    _state.value = StateWrapper.Fail(responseWrapper.message!!)
            }
        }
    }
}