package com.styleru.muro.androidmsk.News

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.styleru.muro.androidmsk.*
import com.styleru.muro.androidmsk.Database.Data.NewsEntity
import com.styleru.muro.androidmsk.Database.DatabaseConverter
import com.styleru.muro.androidmsk.Network.DTO.Response
import com.styleru.muro.androidmsk.Database.NewsRepository
import com.styleru.muro.androidmsk.Network.Network
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : AppCompatActivity(), NewsAdapter.ViewHolderClick, AdapterView.OnItemSelectedListener {

    private val adapter: NewsAdapter = NewsAdapter()
    private lateinit var disposable: Disposable
    private val client = Network.getApiClient()
    private var section = "Movies"

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, NewsListActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            newsRecycler.layoutManager = LinearLayoutManager(this)
        } else {
            newsRecycler.layoutManager = GridLayoutManager(this, 2)
        }

        newsRecycler.adapter = adapter

        loadNewDataDB()

        adapter.setClickListener(this)

        reloadNewsButton.setOnClickListener {
            loadNewData()
        }
    }

    private fun loadNewDataDB() {
        disposable = NewsRepository.getItems(this, section)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null && response.isNotEmpty())
                        onLoadSuccessDB(response)
                    else
                        loadNewData()
                }, { t: Throwable? ->
                    onLoadFailedDB(t)
                })
    }

    private fun loadNewData() {
        reloadNewsButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        newsRecycler.visibility = View.INVISIBLE
        if (!disposable.isDisposed) {
            disposable.dispose()
        }

        disposable = client.getNews(section.toLowerCase().replace(" ", ""), "json")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    onLoadSuccess(response)
                }, { t: Throwable? ->
                    onLoadFailed(t)
                })
    }

    private fun onLoadSuccess(response: Response) {
        NewsRepository.saveItems(this, response.results).observeOn(AndroidSchedulers.mainThread())
                .andThen(NewsRepository.getItems(this, section))
                .subscribe { result ->
                    progressBar.visibility = View.GONE
                    newsRecycler.visibility = View.VISIBLE
                    adapter.items = result
                }
    }

    private fun onLoadSuccessDB(list: List<NewsEntity>) {
        adapter.items = list

        progressBar.visibility = View.GONE
        newsRecycler.visibility = View.VISIBLE
    }

    private fun onLoadFailed(throwable: Throwable?) {
        progressBar.visibility = View.INVISIBLE
        reloadNewsButton.visibility = View.VISIBLE
        Toast.makeText(this, "Internal Server error please try later", Toast.LENGTH_SHORT).show()
    }

    private fun onLoadFailedDB(throwable: Throwable?) {
        Toast.makeText(this, "DB Error", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(id: Long) {
        NewsDetailsActivity.start(id, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val spinner = menu?.findItem(R.id.menu_spinner)?.actionView as Spinner
        ArrayAdapter.createFromResource(this, R.array.sections, android.R.layout.simple_spinner_item)
                .also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = it
                }
        spinner.onItemSelectedListener = this
        return true
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        section = resources.getStringArray(R.array.sections)[position]
        loadNewDataDB()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

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
