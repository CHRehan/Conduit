package io.realworld.android.conduit.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realworld.android.conduit.data.ArticleRepo
import io.realworld.android.conduit.models.entities.Article
import io.realworld.android.conduit.models.responses.ArticleResponse
import io.realworld.android.conduit.network.ConduitAPI
import io.realworld.android.conduit.network.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleViewModel @Inject constructor
    (private val articleRepo: ArticleRepo) : ViewModel() {

    private val _article = MutableLiveData<Resource<ArticleResponse>>()
    val article: LiveData<Resource<ArticleResponse>> = _article


    fun getArticle(slug: String): MutableLiveData<Resource<ArticleResponse>> {

        viewModelScope.launch {

            _article.value = Resource.Loading
            _article.value = articleRepo.getArticleBySlug(slug)
        }

        return _article
    }

}