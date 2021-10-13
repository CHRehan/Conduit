package io.realworld.android.conduit.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.realworld.android.conduit.models.entities.Profile

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "profile")
    val profile: Profile
)