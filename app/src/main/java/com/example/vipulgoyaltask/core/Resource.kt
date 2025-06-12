package com.example.vipulgoyaltask.core

import com.example.vipulgoyaltask.domain.model.PortfolioCalculatedData

sealed class Resource<T> {
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String, val data: T? = null, val errorCode: Int? = null) : Resource<T>()
}