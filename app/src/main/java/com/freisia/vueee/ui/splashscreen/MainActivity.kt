package com.freisia.vueee.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.freisia.vueee.R
import com.freisia.vueee.ui.list.ListActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@MainActivity,
                    ListActivity::class.java
                )
            )
            finish()
        }, 2000)
    }
}
