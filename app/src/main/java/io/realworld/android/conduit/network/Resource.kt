package io.realworld.android.conduit.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?=null,
        val errorBody: ResponseBody?=null,
        val errorMessage: String? = null
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
    object Logout : Resource<Nothing>()


}