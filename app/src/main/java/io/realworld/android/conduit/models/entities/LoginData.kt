package io.realworld.android.conduit.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginData(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
)