package com.styleru.muro.androidmsk

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.styleru.muro.androidmsk.News.NewsListActivity

class IntroActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        if (prefs.getBoolean(INTRO_FLAG, true)) {
            Handler().postDelayed({ NewsListActivity.start(this); finish() }, 1000)
        } else {
            Handler().post { NewsListActivity.start(this); finish() }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onStop() {
        super.onStop()
        prefs.edit()
                .putBoolean(INTRO_FLAG, !prefs.getBoolean(INTRO_FLAG, true))
                .apply()
    }
}
