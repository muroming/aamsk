package com.styleru.muro.androidmsk.News

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.styleru.muro.androidmsk.Data.NewsItem
import com.styleru.muro.androidmsk.*
import com.styleru.muro.androidmsk.Data.Response
import com.styleru.muro.androidmsk.Network.Network
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_news_list.*
import java.io.IOException

class NewsListActivity : AppCompatActivity(), NewsAdapter.ViewHolderClick {

    private val adapter: NewsAdapter = NewsAdapter(this)
    private lateinit var disposable: Disposable
    private val client = Network.getApiClient()
    private var section = "technology"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            newsRecycler.layoutManager = LinearLayoutManager(this)
        } else {
            newsRecycler.layoutManager = GridLayoutManager(this, 2)
        }

        newsRecycler.adapter = adapter

        disposable = client.getNews(section)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    onLoadSuccess(response)
                }, { t: Throwable? ->
                    onLoadFailed(t)
                })

        adapter.setClickListener(this)

        reloadNewsButton.setOnClickListener {
            loadNewData()
        }
    }

    private fun loadNewData() {
        reloadNewsButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        if (!disposable.isDisposed) {
            disposable.dispose()
        }

        adapter.clearItems()

        disposable = client.getNews(section, "json")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    onLoadSuccess(response)
                }, { t: Throwable? ->
                    onLoadFailed(t)
                })
    }

    private fun onLoadSuccess(response: Response) {
        adapter.addNewsItems(response.results)
        progressBar.visibility = View.GONE
        newsRecycler.visibility = View.VISIBLE
    }

    private fun onLoadFailed(throwable: Throwable?) {
        if (throwable is IOException) {
            progressBar.visibility = View.INVISIBLE
            reloadNewsButton.visibility = View.VISIBLE
        }
        Toast.makeText(this, "Internal Server error please try later", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(newsItem: NewsItem) {
        NewsDetailsActivity.start(newsItem, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val spinner = menu?.findItem(R.id.menu_spinner)?.actionView as Spinner
        ArrayAdapter.createFromResource(this, R.array.sections, android.R.layout.simple_spinner_item)
                .also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = it
                }
        return true
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
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
