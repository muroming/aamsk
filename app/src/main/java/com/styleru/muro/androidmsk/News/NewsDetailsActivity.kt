package com.styleru.muro.androidmsk.News

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.styleru.muro.androidmsk.*
import com.styleru.muro.androidmsk.Database.Data.NewsEntity
import com.styleru.muro.androidmsk.Database.DatabaseConverter
import com.styleru.muro.androidmsk.Network.DTO.NewsItem
import com.styleru.muro.androidmsk.Database.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {

    companion object {
        fun start(id: Long, context: Context) {
            val intent = Intent(context, NewsDetailsActivity::class.java)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }

    private var editDialog: AlertDialog? = null
    private lateinit var newsItem: NewsEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        NewsRepository.getItemById(this, intent.getLongExtra(ID, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { item ->
                    newsItem = item
                    newsDetailsWebView.loadUrl(item.url)
                }
    }

    private fun createEditMenu() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null)

        val title = view.findViewById<EditText>(R.id.newsTitle)
        val url = view.findViewById<EditText>(R.id.newsUrl)
        val spinner = view.findViewById<Spinner>(R.id.newsSpinner)

        builder
                .setTitle(getString(R.string.edit_dialog))
                .setView(view)
                .setNegativeButton(getString(R.string.close)) { _, _ -> editDialog?.dismiss(); editDialog = null }
                .setPositiveButton(getString(R.string.save)) { _, _ ->
                    newsItem.title = title.text.toString()
                    newsItem.abstract = url.text.toString()
                    newsItem.section = spinner.selectedItem.toString()
                    NewsRepository.updateItem(this, newsItem)
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                editDialog?.dismiss()
                                editDialog = null
                            }
                }

        builder.create().show()
        title.setText(newsItem.title, TextView.BufferType.EDITABLE)
        url.setText(newsItem.url, TextView.BufferType.EDITABLE)
        ArrayAdapter.createFromResource(this, R.array.sections, android.R.layout.simple_spinner_item)
                .also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = it
                    spinner.setSelection(resources.getStringArray(R.array.sections).indexOf(newsItem.section))
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                R.id.details_edit -> {
                    createEditMenu()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

}
