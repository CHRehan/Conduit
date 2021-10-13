package io.realworld.android.conduit.models.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.realworld.android.conduit.models.entities.UserUpdateData

@JsonClass(generateAdapter = true)
data class UserUpdateRequest(
    @Json(name = "user")
    val user: UserUpdateData
)