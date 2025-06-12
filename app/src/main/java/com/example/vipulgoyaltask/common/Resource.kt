package com.example.demoassignment.common


sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    var message: String? = null,
    var errorCode: Int?
) {
    class Loading<T>(data: T? = null) : Resource<T>(status = Status.LOADING, data, errorCode = 0)
    class Success<T>(data: T) : Resource<T>(status = Status.SUCCESS, data, errorCode = 0)
    class Error<T>(message: String, data: T? = null, errorCode: Int?) :
        Resource<T>(status = Status.ERROR, data, message, errorCode)
}