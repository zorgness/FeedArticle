package com.example.feedarticle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedarticle.dataclass.ArticleDto
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ArticleAdapter(): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    private var articles: MutableList<ArticleDto> = mutableListOf()
    var onShowItemCallback: ((ArticleDto) -> Unit)? = null

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
                tvTitle.text = article.urlImage
                tvCategory.text = getCategoryById(article.categorie)
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
            }
        }
    }

    fun setArticles(list: List<ArticleDto>) {
        articles.clear()
        articles.addAll(list)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle = itemView.findViewById<TextView>(R.id.tv_name_item_rv_article)
        val tvCategory = itemView.findViewById<TextView>(R.id.tv_category_item_rv_article)
        val civImg = itemView.findViewById<CircleImageView>(R.id.civ_image_item_rv_article)

    }

    //to change
    fun getCategoryById(id: Int): String {
        when(id) {
            0 -> "Sport"
            1 -> "Manga"
            else -> "Divers"
        }.let {
            return it
        }
    }

}

