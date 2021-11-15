package com.freisia.vueee.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freisia.vueee.R
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.ui.detail.DetailActivity
import com.freisia.vueee.utils.Constant
import kotlinx.android.synthetic.main.layout_item_card.view.*

class CardFavoriteAdapter<T> internal constructor(private val activity: Activity) :
    PagedListAdapter<T, CardFavoriteAdapter<T>.CardViewHolder>(
        object : DiffUtil.ItemCallback<T>(){
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                var check = false
                if(oldItem is Movie && newItem is Movie){
                    check = oldItem.title == newItem.title && oldItem.poster_path ==
                            newItem.poster_path && oldItem.vote_average == newItem.vote_average
                } else if(oldItem is TV && newItem is TV){
                    check = oldItem.name == newItem.name && oldItem.poster_path ==
                            newItem.poster_path && oldItem.vote_average == newItem.vote_average
                }
                return check
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindUser(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_card,parent,false))
    }

    inner class CardViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val profilImage = v.imageView
        private val profilTitle = v.nameGrid
        private val profilReview = v.review
        private val view = v

        fun bindUser(movie: T?) {
            if(movie is Movie){
                val image = Uri.parse(Constant.BASE_IMAGE_URL + movie.poster_path)
                Glide.with(itemView.context).load(image).error(R.mipmap.ic_launcher_round).into(profilImage)
                profilTitle.text = movie.title
                val review = movie.vote_average * 10
                profilReview.text = itemView.context.getString(R.string.review,review.toString())
                view.card.setOnClickListener {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIL,movie)
                    intent.putExtra(DetailActivity.TYPE_DETAIL,"localmovies")
                    activity.startActivity(intent)
                }
            } else if(movie is TV){
                val image = Uri.parse(Constant.BASE_IMAGE_URL + movie.poster_path)
                Glide.with(itemView.context).load(image).error(R.mipmap.ic_launcher_round).into(profilImage)
                profilTitle.text = movie.name
                val review = movie.vote_average * 10
                profilReview.text = itemView.context.getString(R.string.review,review.toString())
                view.card.setOnClickListener {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIL,movie)
                    intent.putExtra(DetailActivity.TYPE_DETAIL,"localtvshows")
                    activity.startActivity(intent)
                }
            }
        }
    }
}