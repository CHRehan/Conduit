package io.realworld.android.conduit.models.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.realworld.android.conduit.models.entities.UserCreds

@JsonClass(generateAdapter = true)
data class SignupUser(
    @Json(name = "user")
    val userCreds: UserCreds
)