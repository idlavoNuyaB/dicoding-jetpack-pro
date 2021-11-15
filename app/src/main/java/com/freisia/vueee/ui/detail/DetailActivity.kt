package com.freisia.vueee.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.freisia.vueee.R
import com.freisia.vueee.model.Movie
import com.freisia.vueee.ui.daftar.DaftarActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
        const val TYPE_DETAIL = "type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val movie = detailViewModel.getData(intent.getParcelableExtra(EXTRA_DETAIL) as Movie)
        val type = intent.getStringExtra(TYPE_DETAIL)
        val collapsingToolbar  = main
        val appBarLayout = appbar
        appBarLayout.setExpanded(true)
        val image = image
        val rating = rating
        val duration = duration
        val genre = genre
        val broadcast = broadcast
        val description = description
        val review = review
        Glide.with(applicationContext).load(Uri.parse("file:///android_asset/" + type + "/"
                + movie.picture)).error(R.mipmap.ic_launcher_round).into(image)
        collapsingToolbar.title = movie.title
        rating.text = movie.rating
        duration.text = movie.durations
        genre.text = movie.genre
        broadcast.text = movie.broadcast
        description.text = movie.description
        review.text = getString(R.string.review,movie.review.toString())
    }

    private fun initToolbar(){
        val toolbar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext,DaftarActivity::class.java))
        }
    }
}
