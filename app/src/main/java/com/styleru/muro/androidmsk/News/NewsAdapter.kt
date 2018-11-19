package com.styleru.muro.androidmsk.News

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.styleru.muro.androidmsk.Database.Data.NewsEntity
import com.styleru.muro.androidmsk.R
import kotlinx.android.synthetic.main.news_item.view.*
import java.lang.NullPointerException

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var items: List<NewsEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface ViewHolderClick {
        fun onClick(id: Long)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = items[position]
        if (newsItem.section != null) {
            holder.category.text = newsItem.section
        } else {
            holder.category.visibility = View.GONE
        }
        holder.title.text = newsItem.title
        holder.prev.text = newsItem.abstract
        holder.date.text = newsItem.publishedDate
        holder.id = newsItem.id ?: throw NullPointerException("Id is null")

        var url = ""
        if (newsItem.multimedia.isNotEmpty()) {
            url = newsItem.multimedia[0].url
        }
        Glide.with(holder.view)
                .load(url)
                .apply(RequestOptions().placeholder(R.drawable.ic_baseline_panorama_24px))
                .into(holder.image)


    }

    class ViewHolder(val view: View, private val clickListener: ViewHolderClick) : RecyclerView.ViewHolder(view) {
        var id: Long = 0
        val category = view.tv_news_category!!
        val title = view.tv_news_title!!
        val prev = view.tv_news_preview!!
        val date = view.tv_news_date!!
        val image = view.iv_news_image!!

        init {
            view.setOnClickListener { clickListener.onClick(id) }
        }
    }
}