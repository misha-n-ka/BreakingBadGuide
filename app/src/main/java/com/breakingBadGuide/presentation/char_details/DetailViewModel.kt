package com.breakingBadGuide.presentation.char_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breakingBadGuide.data.ResponseWrapper
import com.breakingBadGuide.data.models.CharacterByIdItem
import com.breakingBadGuide.domain.repositories.Repository
import com.breakingBadGuide.presentation.ScreenStateWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    // StateFlow contains current fragment state
    private val _state = MutableStateFlow<ScreenStateWrapper>(ScreenStateWrapper.Empty)
    val state: StateFlow<ScreenStateWrapper> = _state


    fun getCharacterById(id: Int) {
        // Change fragment state to Loading
        _state.value = ScreenStateWrapper.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val responseWrapper: ResponseWrapper<List<CharacterByIdItem>> =
                repository.getCharacterById(id)
            ) {
                // if network request is succeed
                is ResponseWrapper.Success -> {
                    val character = responseWrapper.data?.get(0)
                    _state.value = ScreenStateWrapper.Success(character!!)
                }
                // if network request has error
                is ResponseWrapper.Error ->
                    _state.value = ScreenStateWrapper.Error(responseWrapper.message!!)
            }
        }
    }
}