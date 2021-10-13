package io.realworld.android.conduit.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.android.conduit.R
import io.realworld.android.conduit.databinding.FragmentArticleBinding
import io.realworld.android.conduit.extensions.handleApiError
import io.realworld.android.conduit.extensions.loadImage
import io.realworld.android.conduit.extensions.timeStamp
import io.realworld.android.conduit.extensions.visible
import io.realworld.android.conduit.network.Resource


@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var binding: FragmentArticleBinding? = null

    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        getArticle()

        return binding?.root
    }

    private fun getArticle() {
        arguments?.let { it ->
            val slug: String? = it.getString(getString(R.string.arg_article_id))
            slug?.let {
                articleViewModel.getArticle(it)

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel
            .article
            .observe({ lifecycle }) {
                binding?.articleProgressbar?.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        binding?.apply {
                            val article = it.value.article
                            titleTextView.text = article.title
                            authorTextView.text = article.author.username
                            bodyTextView.text = article.body
                            dateTextView.timeStamp = article.updatedAt
                            avatarImageView.loadImage(article.author.image, true)
                        }
                    }
                    is Resource.Failure -> {
                        handleApiError(it) { getArticle() }
                    }
                }

            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}