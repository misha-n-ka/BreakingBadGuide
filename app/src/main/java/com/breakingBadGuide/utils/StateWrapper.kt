package com.breakingBadGuide.utils

// Wrapper for fragment state
sealed class StateWrapper {
    class Success(val data: Any) : StateWrapper()
    class Fail(val errorText: String) : StateWrapper()
    object Loading : StateWrapper()
    object Empty : StateWrapper()
}
