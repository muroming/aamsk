package com.styleru.muro.androidmsk.News

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.styleru.muro.androidmsk.Data.NewsItem
import com.styleru.muro.androidmsk.R
import kotlinx.android.synthetic.main.news_item.view.*
import java.lang.ref.WeakReference

class NewsAdapter(private val items: List<NewsItem>, private val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    interface ViewHolderClick {
        fun onClick(newsItem: NewsItem)
    }

    private lateinit var clickListener: ViewHolderClick

    fun setClickListener(listener: ViewHolderClick) {
        clickListener = listener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.news_item, p0, false)
        return ViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val newsItem = items[p1]
        p0.category.text = newsItem.category.name
        p0.title.text = newsItem.title
        p0.prev.text = newsItem.previewText
        p0.date.text = newsItem.publishDate.toString()
        p0.item = newsItem
        val imageSize: Int = context.resources.getDimensionPixelSize(R.dimen.news_image_size)
        Picasso.get()
                .load(newsItem.imageUrl)
                .resize(imageSize, imageSize)
                .into(p0.image)
    }

    class ViewHolder(val view: View, val clickListener: ViewHolderClick) : RecyclerView.ViewHolder(view) {
        lateinit var item: NewsItem
        val category = view.tv_news_category!!
        val title = view.tv_news_title!!
        val prev = view.tv_news_preview!!
        val date = view.tv_news_date!!
        val image = view.iv_news_image!!

        init {
            view.setOnClickListener { clickListener.onClick(item) }
        }
    }
}