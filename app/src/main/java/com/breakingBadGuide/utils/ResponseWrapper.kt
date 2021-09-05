package com.breakingBadGuide.utils

// Wrapper for network responses
sealed class ResponseWrapper<T>(val data: T?, val message: String?) {
    class Success<T>(data: T): ResponseWrapper<T>(data, null)
    class Error<T>(errorMessage: String): ResponseWrapper<T>(null, errorMessage)
}