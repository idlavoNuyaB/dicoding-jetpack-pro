package com.freisia.vueee.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.freisia.vueee.R
import com.freisia.vueee.data.local.repository.MovieLocalRepository
import com.freisia.vueee.data.local.repository.TVLocalRepository
import com.freisia.vueee.data.remote.repository.MovieRepository
import com.freisia.vueee.data.remote.repository.TVRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.movie.SearchMovie
import com.freisia.vueee.model.tv.SearchTV
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.ui.favorite.FavoriteActivity
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
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {

    private var coroutineJob : Job? = null
    private var isFavourite by Delegates.notNull<Boolean>()

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
            val detailViewModel:DetailViewModel<MovieRepository,Movie,MovieLocalRepository> by viewModel{parametersOf(type)}
            val movies = intent.getParcelableExtra(EXTRA_DETAIL) as SearchMovie
            coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                detailViewModel.getData(movies.id)
            }
            detailViewModel.isLoading.observeForever(observeLoading())
            detailViewModel.isFound.observeForever {
                if(it){
                    detailViewModel.listData.observeForever{ movie ->
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
                                movie.poster_path)).error(R.mipmap.ic_launcher_round).into(image)
                        collapsingToolbar.title = movie.title
                        var check = 0
                        for(i in movie.releases.countries){
                            if(i.iso_3166_1 == "US"){
                                rating.text = i.certification
                                break
                            }
                            check ++
                            if(check == movie.releases.countries.size - 1){
                                rating.text = "-"
                            }
                        }
                        duration.text = duration(movie.runtime)
                        var temp: String? = ""
                        for(i in 0 until movie.genres.size){
                            temp += if(i != movie.genres.size - 1){
                                movie.genres[i].name + ", "
                            } else {
                                movie.genres[i].name
                            }
                        }
                        genre.text = temp
                        broadcast.text = movie.release_date
                        description.text = movie.overview
                        val reviews = movie.vote_average * 10
                        review.text = getString(R.string.review,reviews.toString())
                        found()
                        detailViewModel.localData?.observeForever {data ->
                            if(!data.isNullOrEmpty()){
                                for(i in data.indices){
                                    if(data[i].id == movie.id){
                                        isFavourite = true
                                        break
                                    } else {
                                        isFavourite = false
                                    }
                                }
                                if(!isFavourite){
                                    fab.setImageResource(R.drawable.ic_action_favorite_off)
                                } else {
                                    fab.setImageResource(R.drawable.ic_action_favorite_on)
                                }
                            } else {
                                isFavourite = false
                                if(!isFavourite){
                                    fab.setImageResource(R.drawable.ic_action_favorite_off)
                                } else {
                                    fab.setImageResource(R.drawable.ic_action_favorite_on)
                                }
                            }
                            fab.setOnClickListener{
                                if(!isFavourite){
                                    detailViewModel.insert(movie)
                                    fab.setImageResource(R.drawable.ic_action_favorite_on)
                                } else {
                                    detailViewModel.delete(movie)
                                    fab.setImageResource(R.drawable.ic_action_favorite_off)
                                }
                            }
                        }
                    }
                } else {
                    notFound()
                }
            }
        }  else if(type =="localmovies"){
            val detailViewModel:DetailViewModel<MovieRepository,Movie,MovieLocalRepository> by viewModel{parametersOf(type)}
            val movies = intent.getParcelableExtra(EXTRA_DETAIL) as Movie
            detailViewModel.isLoading.observeForever(observeLoading())
            detailViewModel.isFound.observeForever {
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
                        movies.poster_path)).error(R.mipmap.ic_launcher_round).into(image)
                collapsingToolbar.title = movies.title
                var check = 0
                for(i in movies.releases.countries){
                    if(i.iso_3166_1 == "US"){
                        rating.text = i.certification
                        break
                    }
                    check ++
                    if(check == movies.releases.countries.size - 1){
                        rating.text = "-"
                    }
                }
                duration.text = duration(movies.runtime)
                var temp: String? = ""
                for(i in 0 until movies.genres.size){
                    temp += if(i != movies.genres.size - 1){
                        movies.genres[i].name + ", "
                    } else {
                        movies.genres[i].name
                    }
                }
                genre.text = temp
                broadcast.text = movies.release_date
                description.text = movies.overview
                val reviews = movies.vote_average * 10
                review.text = getString(R.string.review,reviews.toString())
                found()
                detailViewModel.localData?.observeForever {data ->
                    if(!data.isNullOrEmpty()){
                        for(i in data.indices){
                            if(data[i].id == movies.id){
                                isFavourite = true
                                break
                            } else {
                                isFavourite = false
                            }
                        }
                        if(!isFavourite){
                            fab.setImageResource(R.drawable.ic_action_favorite_off)
                        } else {
                            fab.setImageResource(R.drawable.ic_action_favorite_on)
                        }
                    } else {
                        isFavourite = false
                        if(!isFavourite){
                            fab.setImageResource(R.drawable.ic_action_favorite_off)
                        } else {
                            fab.setImageResource(R.drawable.ic_action_favorite_on)
                        }
                    }
                    fab.setOnClickListener{
                        if(!isFavourite){
                            detailViewModel.insert(movies)
                            fab.setImageResource(R.drawable.ic_action_favorite_on)
                        } else {
                            detailViewModel.delete(movies)
                            fab.setImageResource(R.drawable.ic_action_favorite_off)
                        }
                    }
                }
            }
        } else if(type == "tvshows"){
            coroutineJob?.cancel()
            val detailViewModel by viewModel<DetailViewModel<TVRepository,TV,TVLocalRepository>> {parametersOf(type)}
            val movie = intent.getParcelableExtra(EXTRA_DETAIL) as SearchTV
            coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                detailViewModel.getData(movie.id)
            }
            detailViewModel.isLoading.observeForever(observeLoading())
            detailViewModel.isFound.observeForever {
                if(it){
                    detailViewModel.listData.observeForever{ its ->
                        detailViewModel.rate.observeForever{rate ->
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
                            rating.text = rate
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
                            detailViewModel.localData?.observeForever {data ->
                                if(!data.isNullOrEmpty()){
                                    for(i in data.indices){
                                        if(data[i].id == movie.id){
                                            isFavourite = true
                                            break
                                        } else {
                                            isFavourite = false
                                        }
                                    }
                                    if(!isFavourite){
                                        fab.setImageResource(R.drawable.ic_action_favorite_off)
                                    } else {
                                        fab.setImageResource(R.drawable.ic_action_favorite_on)
                                    }
                                } else {
                                    isFavourite = false
                                    if(!isFavourite){
                                        fab.setImageResource(R.drawable.ic_action_favorite_off)
                                    } else {
                                        fab.setImageResource(R.drawable.ic_action_favorite_on)
                                    }
                                }
                                fab.setOnClickListener{
                                    if(!isFavourite){
                                        detailViewModel.insert(its)
                                        fab.setImageResource(R.drawable.ic_action_favorite_on)
                                    } else {
                                        detailViewModel.delete(its)
                                        fab.setImageResource(R.drawable.ic_action_favorite_off)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    notFound()
                }
            }
        } else if(type == "localtvshows"){
            coroutineJob?.cancel()
            val detailViewModel by viewModel<DetailViewModel<TVRepository,TV,TVLocalRepository>> {parametersOf("tvshows")}
            val movie = intent.getParcelableExtra(EXTRA_DETAIL) as TV
            coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                detailViewModel.getData(movie.id)
            }
            detailViewModel.isLoading.observeForever(observeLoading())
            detailViewModel.isFound.observeForever {
                detailViewModel.rate.observeForever{rate ->
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
                            movie.poster_path)).error(R.mipmap.ic_launcher_round).into(image)
                    rating.text = rate
                    collapsingToolbar.title = movie.name
                    duration.text = duration(movie.episode_run_time[0])
                    var temp: String? = ""
                    for(i in 0 until movie.genres.size){
                        temp += if(i != movie.genres.size - 1){
                            movie.genres[i].name + ", "
                        } else {
                            movie.genres[i].name
                        }
                    }
                    genre.text = temp
                    broadcast.text = movie.first_air_date
                    description.text = movie.overview
                    val reviews = movie.vote_average * 10
                    review.text = getString(R.string.review,reviews.toString())
                    found()
                    detailViewModel.localData?.observeForever {data ->
                        if(!data.isNullOrEmpty()){
                            for(i in data.indices){
                                if(data[i].id == movie.id){
                                    isFavourite = true
                                    break
                                } else {
                                    isFavourite = false
                                }
                            }
                            if(!isFavourite){
                                fab.setImageResource(R.drawable.ic_action_favorite_off)
                            } else {
                                fab.setImageResource(R.drawable.ic_action_favorite_on)
                            }
                        } else {
                            isFavourite = false
                            if(!isFavourite){
                                fab.setImageResource(R.drawable.ic_action_favorite_off)
                            } else {
                                fab.setImageResource(R.drawable.ic_action_favorite_on)
                            }
                        }
                        fab.setOnClickListener{
                            if(!isFavourite){
                                detailViewModel.insert(movie)
                                fab.setImageResource(R.drawable.ic_action_favorite_on)
                            } else {
                                detailViewModel.delete(movie)
                                fab.setImageResource(R.drawable.ic_action_favorite_off)
                            }
                        }
                    }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
