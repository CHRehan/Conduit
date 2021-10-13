package io.realworld.android.conduit.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.android.conduit.models.entities.Article
import io.realworld.android.conduit.R
import io.realworld.android.conduit.databinding.FragmentFeedBinding
import io.realworld.android.conduit.extensions.handleApiError
import io.realworld.android.conduit.network.Resource


@AndroidEntryPoint
class MyFeedFragment : Fragment() {
    private var binding: FragmentFeedBinding? = null
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: ArticleFeedAdapter
    private val articles: ArrayList<Article> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        feedAdapter = ArticleFeedAdapter(articles)
        feedAdapter.openArticle = { openArticle(it) }

        binding?.apply {
            feedRecyclerView.layoutManager = LinearLayoutManager(context)
            feedRecyclerView.adapter = feedAdapter

        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchMyFeed()

        viewModel.feed.observe({ lifecycle }) {
            when (it) {
                is Resource.Success -> {
                    articles.addAll(it.value.articles)
                    feedAdapter.notifyDataSetChanged()
                }
                is Resource.Failure -> {
                    handleApiError(it, retry = { viewModel.fetchMyFeed() })
                }
            }


        }

    }

    private fun openArticle(slug: String) {
        findNavController().navigate(

            R.id.open_article_my_feed,
            bundleOf(
                getString(R.string.arg_article_id) to slug
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}