package io.realworld.android.conduit.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realworld.android.conduit.models.entities.Article
import io.realworld.android.conduit.R
import io.realworld.android.conduit.databinding.ListItemArticleBinding
import io.realworld.android.conduit.extensions.loadImage
import io.realworld.android.conduit.extensions.timeStamp


class ArticleFeedAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleFeedAdapter.FeedViewHolder>() {
    lateinit var openArticle: (slug: String) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.list_item_article,
                    parent,
                    false
                )
        )

    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        ListItemArticleBinding.bind(holder.itemView).apply {
            val article = articles[position]

            authorTextView.text = article.author.username
            titleTextView.text = article.title
            bodySnippetTextView.text = article.body
            dateTextView.timeStamp = article.createdAt
            avatarImageView.loadImage(article.author.image, true)

            root.setOnClickListener { openArticle(article.slug) }


        }

    }

    override fun getItemCount(): Int {
        return articles.size
    }


    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}