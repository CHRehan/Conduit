package io.realworld.android.conduit.network

import io.realworld.android.conduit.models.requests.ArticlesResponse
import io.realworld.android.conduit.models.requests.LoginRequest
import io.realworld.android.conduit.models.requests.SignupRequest
import io.realworld.android.conduit.models.responses.ArticleResponse
import io.realworld.android.conduit.models.responses.TagsResponse
import io.realworld.android.conduit.models.responses.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface ConduitAPI {

    @POST("users")
    suspend fun signupUser(
        @Body userCreds: SignupRequest
    ): UserResponse

    @POST("users/login")
    suspend fun loginUser(
        @Body userCreds: LoginRequest
    ): UserResponse

    @GET("articles")
    suspend fun getArticles(
        @Query("author") author: String? = null,
        @Query("favourited") favourited: String? = null,
        @Query("tag") tag: String? = null
    ): ArticlesResponse

    @GET("articles/{slug}")
    suspend fun getArticleBySlug(
        @Path("slug") slug: String
    ): ArticleResponse

    @GET("tags")
    suspend fun getTags(): TagsResponse

}