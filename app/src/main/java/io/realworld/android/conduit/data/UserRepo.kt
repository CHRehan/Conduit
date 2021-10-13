package io.realworld.android.conduit.data

import io.realworld.android.conduit.models.entities.LoginData
import io.realworld.android.conduit.models.entities.SignupData
import io.realworld.android.conduit.models.entities.UserUpdateData
import io.realworld.android.conduit.models.requests.LoginRequest
import io.realworld.android.conduit.models.requests.SignupRequest
import io.realworld.android.conduit.models.requests.UserUpdateRequest
import io.realworld.android.conduit.models.responses.UserResponse
import io.realworld.android.conduit.network.ConduitAPI
import io.realworld.android.conduit.network.ConduitAuthAPI
import io.realworld.android.conduit.network.Resource
import javax.inject.Inject


class UserRepo @Inject constructor(
    private val publicApi: ConduitAPI,
    private val authApi: ConduitAuthAPI
) : BaseRepository() {

    suspend fun getCurrentUser(): Resource<UserResponse> {
        return safeApiCall { authApi.getCurrentUser() }
    }

    suspend fun login(
        email: String,
        password: String
    ): Resource<UserResponse> {
        return safeApiCall { publicApi.loginUser(LoginRequest(LoginData(email, password))) }
    }

    suspend fun signup(
        username: String,
        email: String,
        password: String
    ): Resource<UserResponse> {
        return safeApiCall {
            publicApi.signupUser(
                SignupRequest(
                    SignupData(
                        email,
                        password,
                        username
                    )
                )
            )
        }
    }

    suspend fun update(
        image: String?,
        bio: String?,
        username: String?,
        email: String?,
        password: String?
    ): Resource<UserResponse> {
        return safeApiCall {
            authApi.updateCurrentUser(
                UserUpdateRequest(
                    UserUpdateData(
                        image = image,
                        bio = bio,
                        username = username,
                        email = email,
                        password = password
                    )
                )
            )
        }

    }

}