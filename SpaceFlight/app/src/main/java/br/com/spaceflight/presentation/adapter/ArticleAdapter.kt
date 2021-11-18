package br.com.spaceflight.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.spaceflight.R
import br.com.spaceflight.core.util.extensions.formatDate
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.databinding.ItemArticleBinding
import com.bumptech.glide.Glide

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var articles: List<Articles>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context).load(article.imageUrl).into(imageArticle)
            tvTitleArticle.text = article.title
            tvPublishedAt.text = formatDate(article.publishedAt)
            setHasLaunch(article, holder)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(article)
            }
        }
    }

    private fun ItemArticleBinding.setHasLaunch(
        article: Articles,
        holder: ArticleViewHolder
    ) {
        itemLaunchCh.visibility = if (article.hasLaunches()) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        val count = article.getLaunchesCount()
        itemLaunchCh.text = holder.itemView.resources.getQuantityString(
            R.plurals.numberOfLaunchEvents,
            count,
            count
        )
    }

    override fun getItemCount(): Int = articles.size

    private var onItemClickListener: ((Articles) -> Unit)? = null
    fun setOnClickListener(onItemClick: (Articles) -> Unit) {
        onItemClickListener = onItemClick
    }
}