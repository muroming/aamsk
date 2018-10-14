package com.styleru.muro.androidmsk.News

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.styleru.muro.androidmsk.*
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {

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
