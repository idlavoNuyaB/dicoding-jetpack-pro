package com.freisia.vueee.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.freisia.vueee.R
import com.freisia.vueee.data.repository.MovieRepository
import com.freisia.vueee.data.repository.TVRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.movie.SearchMovie
import com.freisia.vueee.model.tv.SearchTV
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.ui.daftar.DaftarActivity
import com.freisia.vueee.utils.Constant
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.loadings
import kotlinx.android.synthetic.main.activity_detail.nfs
import kotlinx.android.synthetic.main.activity_detail.white_views
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    private var coroutineJob : Job? = null

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
        const val TYPE_DETAIL = "type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        val type = intent.getStringExtra(TYPE_DETAIL)
        if(type == "movies"){
            coroutineJob?.cancel()
            val detailViewModel:DetailViewModel<MovieRepository,Movie> by viewModel{parametersOf(type)}
            val movie = intent.getParcelableExtra(EXTRA_DETAIL) as SearchMovie
            coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                detailViewModel.getData(movie.id)
            }
            detailViewModel.isLoading.observeForever(observeLoading())
            detailViewModel.isFound.observeForever {
                if(it){
                    if(type == "movies") {
                        detailViewModel.listData.observeForever(observeMovie())
                    }
                } else {
                    notFound()
                }
            }
        } else {
            coroutineJob?.cancel()
            val detailViewModel by viewModel<DetailViewModel<TVRepository,TV>> {parametersOf(type)}
            val movie = intent.getParcelableExtra(EXTRA_DETAIL) as SearchTV
            coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                detailViewModel.getData(movie.id)
            }
            detailViewModel.isLoading.observeForever(observeLoading())
            detailViewModel.isFound.observeForever {
                if(it){
                    if(type == "tvshows") {
                        detailViewModel.listData.observeForever{ its ->
                            detailViewModel.rate.observeForever(ratingTV(its))
                        }
                    }
                } else {
                    notFound()
                }
            }
        }
    }

    override fun onPause() {
        coroutineJob?.cancel()
        super.onPause()
    }

    override fun onDestroy() {
        coroutineJob?.cancel()
        super.onDestroy()
    }

    private fun initToolbar(){
        val toolbar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun notFound() {
        view1.visibility = View.GONE
        view2.visibility = View.GONE
        view3.visibility = View.GONE
        view4.visibility = View.GONE
        view5.visibility = View.GONE
        rating.visibility = View.GONE
        ratings.visibility = View.GONE
        genre.visibility = View.GONE
        genres.visibility = View.GONE
        duration.visibility = View.GONE
        durations.visibility = View.GONE
        broadcast.visibility = View.GONE
        broadcasts.visibility = View.GONE
        review.visibility = View.GONE
        reviews.visibility = View.GONE
        description.visibility = View.GONE
        descriptions.visibility = View.GONE
        loadings.visibility = View.GONE
        white_views.visibility = View.GONE
        nfs.visibility = View.VISIBLE
    }

    private fun found() {
        if(nfs.visibility == View.VISIBLE){
            nfs.visibility = View.GONE
        }
        loadings.visibility = View.GONE
        white_views.visibility = View.GONE
    }

    private fun ratingTV(its: TV): Observer<String>{
        return Observer {
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
            Glide.with(applicationContext).load(Uri.parse(Constant.BASE_IMAGE_URL +
                    its.poster_path)).error(R.mipmap.ic_launcher_round).into(image)
            rating.text = it
            collapsingToolbar.title = its.name
            duration.text = duration(its.episode_run_time[0])
            var temp: String? = ""
            for(i in 0 until its.genres.size){
                temp += if(i != its.genres.size - 1){
                    its.genres[i].name + ", "
                } else {
                    its.genres[i].name
                }
            }
            genre.text = temp
            broadcast.text = its.first_air_date
            description.text = its.overview
            val reviews = its.vote_average * 10
            review.text = getString(R.string.review,reviews.toString())
            found()
        }
    }

    private fun observeMovie() : Observer<Movie> {
        return Observer{
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
            Glide.with(applicationContext).load(Uri.parse(Constant.BASE_IMAGE_URL +
                    it.poster_path)).error(R.mipmap.ic_launcher_round).into(image)
            collapsingToolbar.title = it.title
            for(i in it.releases.countries){
                if(i.iso_3166_1 == "US"){
                    rating.text = i.certification
                    break
                }
            }
            duration.text = duration(it.runtime)
            var temp: String? = ""
            for(i in 0 until it.genres.size){
                temp += if(i != it.genres.size - 1){
                    it.genres[i].name + ", "
                } else {
                    it.genres[i].name
                }
            }
            genre.text = temp
            broadcast.text = it.release_date
            description.text = it.overview
            val reviews = it.vote_average * 10
            review.text = getString(R.string.review,reviews.toString())
            found()
        }
    }

    private fun duration(durations: Int) : String{
        var hour = 0
        var durasi = durations
        while(durasi >= 60){
            hour++
            durasi -= 60
        }
        return if(durations >= 60){
            hour.toString() + "h "+ durasi.toString() + "m"
        } else {
            durasi.toString() + "m"
        }
    }

    private fun observeLoading() : Observer<Boolean> {
        return Observer {
            if(it){
                loadings.visibility = View.VISIBLE
                white_views.visibility = View.VISIBLE
            } else {
                loadings.visibility = View.GONE
                white_views.visibility = View.GONE
            }
        }
    }
}
