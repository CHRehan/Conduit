package io.realworld.android.conduit.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.realworld.android.conduit.models.entities.User

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "user")
    val user: User
)