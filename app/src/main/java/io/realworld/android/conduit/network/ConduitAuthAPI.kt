package io.realworld.android.conduit.network

import io.realworld.android.conduit.models.requests.ArticlesResponse
import io.realworld.android.conduit.models.requests.UserUpdateRequest
import io.realworld.android.conduit.models.responses.ArticleResponse
import io.realworld.android.conduit.models.responses.ProfileResponse
import io.realworld.android.conduit.models.responses.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface ConduitAuthAPI {

    @GET("user")
    suspend fun getCurrentUser(): UserResponse

    @PUT("user")
    suspend fun updateCurrentUser(
        @Body userUpdateRequest: UserUpdateRequest
    ): UserResponse

    @GET("profiles/{username}")
    suspend fun getProfile(
        @Path("username") username: String
    ): ProfileResponse

    @POST("profiles/{username}/follow")
    suspend fun followProfile(
        @Path("username") username: String
    ): ProfileResponse

    @DELETE("profiles/{username}/follow")
    suspend fun unfollowProfile(
        @Path("username") username: String
    ): ProfileResponse

    @GET("articles/feed")
    suspend fun getFeedArticles(): ArticlesResponse

    @POST("articles/{slug}/favorite")
    suspend fun favoriteArticle(
        @Path("slug") slug: String
    ): ArticleResponse

    @DELETE("articles/{slug}/favorite")
    suspend fun unfavoriteArticle(
        @Path("slug") slug: String
    ): ArticleResponse
}