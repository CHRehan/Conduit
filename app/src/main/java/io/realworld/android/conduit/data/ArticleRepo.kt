package io.realworld.android.conduit.data

import io.realworld.android.conduit.network.ConduitAPI
import io.realworld.android.conduit.network.ConduitAuthAPI


import javax.inject.Inject


class ArticleRepo @Inject constructor(
    private val publicApi: ConduitAPI,
    private val authApi: ConduitAuthAPI
) : BaseRepository() {


    suspend fun getFeed() = safeApiCall { publicApi.getArticles() }

    suspend fun getMyFeed() = safeApiCall { authApi.getFeedArticles() }

    suspend fun getArticleBySlug(slug: String) = safeApiCall { publicApi.getArticleBySlug(slug) }

}