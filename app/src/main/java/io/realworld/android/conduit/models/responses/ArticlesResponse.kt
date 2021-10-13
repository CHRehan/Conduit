package io.realworld.android.conduit.models.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.realworld.android.conduit.models.entities.Article

@JsonClass(generateAdapter = true)
data class ArticlesResponse(
    @Json(name = "articles")
    val articles: List<Article>,
    @Json(name = "articlesCount")
    val articlesCount: Int
)