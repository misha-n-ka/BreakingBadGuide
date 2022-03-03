package com.breakingBadGuide.presentation

// Wrapper for fragment state
sealed class ScreenStateWrapper {
    class Success<T>(val data: T) : ScreenStateWrapper()
    class Error(val errorText: String) : ScreenStateWrapper()
    object Loading : ScreenStateWrapper()
    object Empty : ScreenStateWrapper()
}
