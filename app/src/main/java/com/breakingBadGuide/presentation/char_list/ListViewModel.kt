package com.breakingBadGuide.presentation.char_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breakingBadGuide.domain.repositories.Repository
import com.breakingBadGuide.data.ResponseWrapper
import com.breakingBadGuide.presentation.ScreenStateWrapper
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
    private val _state = MutableStateFlow<ScreenStateWrapper>(ScreenStateWrapper.Empty)
    val state: StateFlow<ScreenStateWrapper> = _state

    fun getAllCharacters() {
        // Change fragment state to Loading
        _state.value = ScreenStateWrapper.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllCharacters()
            when (response) {
                // if network request is succeed
                is ResponseWrapper.Success -> {
                    val allCharsList = response.data
                    _state.value =
                        ScreenStateWrapper.Success(allCharsList!!)
                }
                // if network request has error
                is ResponseWrapper.Error ->
                    _state.value = ScreenStateWrapper.Error(response.message ?: "Unknown Error")
            }
        }
    }
}