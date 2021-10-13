package io.realworld.android.conduit.network

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

interface SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    is UnknownHostException -> {
                        Resource.Failure(false, errorMessage = throwable.message)
                    }
                    is JsonDataException -> {
                        Resource.Failure(false, errorMessage = throwable.message)

                    }

                    else -> {
                        Resource.Failure(false, errorMessage = "Something went wrong")
                    }
                }
            } finally {
                Resource.Failure(false, errorMessage = "Something went wrong")

            }
        }
    }
}