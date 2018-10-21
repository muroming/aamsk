package com.styleru.muro.androidmsk.News

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.styleru.muro.androidmsk.Data.DataUtils
import com.styleru.muro.androidmsk.Data.NewsItem
import com.styleru.muro.androidmsk.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news_list.*
import java.lang.Exception

class NewsListActivity : AppCompatActivity(), NewsAdapter.ViewHolderClick {

    private val adapter: NewsAdapter = NewsAdapter(this)
    private lateinit var disposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            newsRecycler.layoutManager = LinearLayoutManager(this)
        } else {
            newsRecycler.layoutManager = GridLayoutManager(this, 2)
        }

        newsRecycler.adapter = adapter

        val newsObservable: Observable<NewsItem> = Observable.create {
            try {
                val items = DataUtils.generateNews()
                for (item in items) {
                    it.onNext(item)
                    Thread.sleep(1000)
                }
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }

        disposable = newsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {
                    adapter.addItem(it)
                    Log.d("LOG_TAG", it.title)
                },
                {

                },
                {
                    progress_bar.visibility = View.INVISIBLE
                    newsRecycler.visibility = View.VISIBLE
                })

        adapter.setClickListener(this)

    }

    override fun onClick(newsItem: NewsItem) {
        NewsDetailsActivity.start(newsItem, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
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
