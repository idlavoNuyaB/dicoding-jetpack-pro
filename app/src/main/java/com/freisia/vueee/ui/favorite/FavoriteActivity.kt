package com.freisia.vueee.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.freisia.vueee.R
import com.freisia.vueee.adapter.SectionPagerFavoriteAdapter
import com.freisia.vueee.ui.list.ListActivity
import com.freisia.vueee.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.activity_favorite.*


class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initToolbar()
        initTabLayout()
    }

    private fun initToolbar() {
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
            val intent = Intent(this, ListActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun initTabLayout(){
        val sectionPagerAdapter = SectionPagerFavoriteAdapter(
            this,
            supportFragmentManager
        )
        view_pagers.adapter = sectionPagerAdapter
        view_pagers.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    EspressoIdlingResource.increment()
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                super.onPageScrollStateChanged(state)
            }
        })
        tabs.setupWithViewPager(view_pagers)
    }
}