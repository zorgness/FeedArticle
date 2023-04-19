package com.example.feedarticle

import android.content.Intent
import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticle.dataclass.ArticleDto
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import getArticleColor
import getCategoryById

class ArticleAdapter(): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    private var articles: MutableList<ArticleDto> = mutableListOf()
    var onShowItemCallback: ((ArticleDto, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ArticleViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv_article, parent, false).let {
            return ArticleViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {

        articles[position].let {article ->

            with(holder) {
                tvTitle.text = article.titre
                tvCategory.text =  getCategoryById(article.categorie)

                if (article.urlImage.isEmpty()) {
                    Picasso.get()
                        .load(android.R.color.transparent)
                        .resize(300, 300)
                        .into(civImg)
                } else {
                   Picasso.get()
                   .load(article.urlImage).error(R.drawable.ic_launcher_foreground)
                   .resize(300, 300)
                   .into(civImg)
                }

                articleLayout.setBackgroundColor(Color.parseColor(getArticleColor(article.categorie)))
                articleLayout.setOnClickListener {
                    onShowItemCallback?.invoke(article, position)
                }
            }
        }
    }

    fun setArticles(list: List<ArticleDto>) {
        articles.clear()
        articles.addAll(list)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val articleLayout = itemView.findViewById<ConstraintLayout>(R.id.article_layout)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_name_item_rv_article)
        val tvCategory = itemView.findViewById<TextView>(R.id.tv_category_item_rv_article)
        val civImg = itemView.findViewById<CircleImageView>(R.id.civ_image_item_rv_article)

    }



}

