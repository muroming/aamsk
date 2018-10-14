package com.styleru.muro.androidmsk.News

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.styleru.muro.androidmsk.Data.DataUtils
import com.styleru.muro.androidmsk.Data.NewsItem
import com.styleru.muro.androidmsk.*
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : AppCompatActivity(), NewsAdapter.ViewHolderClick {

    private val adapter: NewsAdapter = NewsAdapter(DataUtils.generateNews(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            rv_news_recycler.layoutManager = LinearLayoutManager(this)
        } else {
            rv_news_recycler.layoutManager = GridLayoutManager(this, 2)
        }

        adapter.setClickListener(this)
        rv_news_recycler.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onClick(newsItem: NewsItem) {
        val intent = Intent(this, NewsDetailsActivity::class.java)
        intent.putExtra(TEXT, newsItem.fullText)
        intent.putExtra(TITLE, newsItem.title)
        intent.putExtra(DATE, newsItem.publishDate)
        intent.putExtra(IMAGE_URL, newsItem.imageUrl)

        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.menu_open_about -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
