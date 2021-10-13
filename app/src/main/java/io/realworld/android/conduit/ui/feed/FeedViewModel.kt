package io.realworld.android.conduit.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import io.realworld.android.conduit.data.ArticleRepo
import io.realworld.android.conduit.models.requests.ArticlesResponse
import io.realworld.android.conduit.network.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val articleRepo: ArticleRepo
) : ViewModel() {


    private val _feed = MutableLiveData<Resource<ArticlesResponse>>()

    val feed: LiveData<Resource<ArticlesResponse>> = _feed

    fun fetchGlobalFeed() {
        viewModelScope.launch {
            _feed.value = Resource.Loading
            _feed.value = articleRepo.getFeed()

        }
    }

    fun fetchMyFeed() {
        viewModelScope.launch {
            _feed.value = Resource.Loading
            _feed.value = articleRepo.getMyFeed()

        }
    }
}