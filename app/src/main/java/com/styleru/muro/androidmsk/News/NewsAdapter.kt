package com.styleru.muro.androidmsk.News

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.styleru.muro.androidmsk.Data.Multimedia
import com.styleru.muro.androidmsk.Data.NewsItem
import com.styleru.muro.androidmsk.R
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val items: MutableList<NewsItem> = ArrayList()

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
        if(newsItem.section != null) {
            p0.category.text = newsItem.section
        } else {
            p0.category.visibility = View.GONE
        }
        p0.title.text = newsItem.title
        p0.prev.text = newsItem.abstract
        p0.date.text = newsItem.publishedDate
        p0.item = newsItem

        if (newsItem.multimedia!= null){
            var url = ""
            if (newsItem.multimedia.isNotEmpty()){
                url = newsItem.multimedia[0].url
            }
            Glide.with(p0.view)
                    .load(url)
                    .apply(RequestOptions().placeholder(R.drawable.ic_baseline_panorama_24px))
                    .into(p0.image)

        }
    }

    fun addNewsItems(newsItems: List<NewsItem>){
        items.addAll(newsItems)
        notifyItemRangeInserted(items.size - newsItems.size, newsItems.size)
    }

    fun clearItems(){
        items.clear()
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