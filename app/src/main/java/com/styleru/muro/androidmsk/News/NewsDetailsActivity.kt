package com.styleru.muro.androidmsk.News

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.styleru.muro.androidmsk.*
import com.styleru.muro.androidmsk.Data.NewsItem
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {

    companion object {
        fun start(newsItem: NewsItem, context: Context) {
            val intent = Intent(context, NewsDetailsActivity::class.java)
            intent.putExtra(TEXT, newsItem.fullText)
            intent.putExtra(TITLE, newsItem.title)
            intent.putExtra(DATE, newsItem.publishDate)
            intent.putExtra(IMAGE_URL, newsItem.imageUrl)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val intent = intent

        tv_news_details_title.text = intent.getStringExtra(TITLE)
        tv_news_details_text.text = intent.getStringExtra(TEXT)
        tv_news_details_date.text = intent.getStringExtra(DATE)
        Picasso.get()
                .load(intent.getStringExtra(IMAGE_URL))
                .into(iv_news_details_image)
    }
}
