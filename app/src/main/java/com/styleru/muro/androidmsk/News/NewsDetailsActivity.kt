package com.styleru.muro.androidmsk.News

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.styleru.muro.androidmsk.*
import com.styleru.muro.androidmsk.Data.NewsItem
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {

    companion object {
        fun start(newsItem: NewsItem, context: Context) {
            val intent = Intent(context, NewsDetailsActivity::class.java)
            intent.putExtra(URL, newsItem.url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        val url = intent.getStringExtra(URL)

        newsDetailsWebView.loadUrl(url)
    }
}
