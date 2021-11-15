package com.freisia.vueee.ui.daftar


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.freisia.vueee.R
import com.freisia.vueee.adapter.SectionPagerAdapter
import kotlinx.android.synthetic.main.activity_daftar.*

class DaftarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)
        initToolbar()
        initTabLayout()
    }

    private fun initToolbar(){
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowCustomEnabled(true)
            setCustomView(R.layout.toolbar)
            elevation = 0f
        }
        val actionBar = supportActionBar
        val toolbar = actionBar?.customView?.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)
        toolbar.contentInsetEnd
        toolbar.setPadding(0, 0, 0, 0)
        val view = toolbar.getChildAt(0)
        view.setOnClickListener {
            val intent = Intent(this@DaftarActivity,DaftarActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun initTabLayout(){
        val sectionPagerAdapter = SectionPagerAdapter(
            this,
            supportFragmentManager
        )
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager((view_pager))
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}
