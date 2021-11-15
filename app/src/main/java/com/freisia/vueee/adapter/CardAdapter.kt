package com.freisia.vueee.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freisia.vueee.R
import com.freisia.vueee.model.Movie
import com.freisia.vueee.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.layout_item_card.view.*


class CardAdapter(movie: ArrayList<Movie>, private val type: String) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    private var filtered : ArrayList<Movie> = ArrayList()

    init {
        filtered = movie

    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{
        private val profilImage = v.imageView
        private val profilTitle = v.nameGrid
        private val profilReview = v.review
        init{
            v.setOnClickListener(this)
        }
        fun bindUser(movie: Movie){
            val image = Uri.parse("file:///android_asset/" + type + "/" + movie.picture)
            Glide.with(itemView.context).load(image).error(R.mipmap.ic_launcher_round).into(profilImage)
            profilTitle.text = movie.title
            profilReview.text = itemView.context.getString(R.string.review,movie.review.toString())
        }

        override fun onClick(v: View?) {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, filtered[adapterPosition])
            intent.putExtra(DetailActivity.TYPE_DETAIL,type)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_card,parent,false))
    }

    override fun getItemCount(): Int = filtered.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        filtered[position].let { holder.bindUser(it) }
    }
}