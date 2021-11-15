package com.freisia.vueee.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freisia.vueee.R
import com.freisia.vueee.model.movie.SearchMovie
import com.freisia.vueee.model.tv.SearchTV
import com.freisia.vueee.ui.detail.DetailActivity
import com.freisia.vueee.utils.Constant
import kotlinx.android.synthetic.main.layout_item_card.view.*

class CardAdapter<T>(movie: ArrayList<T>, private val type: String, recyclerView: RecyclerView) : RecyclerView.Adapter<CardAdapter.CardViewHolder<T>>() {
    private var filtered : ArrayList<T> = ArrayList()
    private var loading: Boolean = false
    lateinit var onLoadMoreListener: OnLoadMoreListener

    init {
        filtered = movie
        if(recyclerView.layoutManager is GridLayoutManager){
            val linearLayoutManager = recyclerView.layoutManager as GridLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    if(!loading && totalItemCount - 1 <= lastVisible && lastVisible > filtered.size - 2){
                        onLoadMoreListener.onGridLoadMore()
                        loading = true
                    }
                }
            })
        }
    }

    inner class ViewHolder(v: View) : CardViewHolder<T>(v), View.OnClickListener{
        private val profilImage = v.imageView
        private val profilTitle = v.nameGrid
        private val profilReview = v.review
        init{
            v.setOnClickListener(this)
        }
        override fun bindUser(movie: T) {
            if(movie is SearchMovie){
                val image = Uri.parse(Constant.BASE_IMAGE_URL + movie.poster_path)
                Glide.with(itemView.context).load(image).error(R.mipmap.ic_launcher_round).into(profilImage)
                profilTitle.text = movie.title
                val review = movie.vote_average * 10
                profilReview.text = itemView.context.getString(R.string.review,review.toString())
            } else if(movie is SearchTV){
                val image = Uri.parse(Constant.BASE_IMAGE_URL + movie.poster_path)
                Glide.with(itemView.context).load(image).error(R.mipmap.ic_launcher_round).into(profilImage)
                profilTitle.text = movie.name
                val review = movie.vote_average * 10
                profilReview.text = itemView.context.getString(R.string.review,review.toString())
            }
        }

        override fun onClick(v: View?) {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            if(filtered[adapterPosition] is SearchMovie){
                intent.putExtra(DetailActivity.EXTRA_DETAIL, filtered[adapterPosition] as SearchMovie)
            } else if(filtered[adapterPosition] is SearchTV) {
                intent.putExtra(DetailActivity.EXTRA_DETAIL, filtered[adapterPosition] as SearchTV)
            }
            intent.putExtra(DetailActivity.TYPE_DETAIL,type)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_card,parent,false))
    }

    override fun getItemCount(): Int = filtered.size

    override fun onBindViewHolder(holder: CardViewHolder<T>, position: Int) {
        filtered[position].let { holder.bindUser(it) }
    }

    interface OnLoadMoreListener {
        fun onGridLoadMore()
    }

    fun setLoad(){
        loading = false
    }

    fun setData(result: ArrayList<T>){
        filtered = result
        notifyDataSetChanged()
    }

    fun resetData(){
        filtered = ArrayList()
        notifyDataSetChanged()
    }

    abstract class CardViewHolder<T> (v: View) : RecyclerView.ViewHolder(v){
        abstract fun bindUser(movie: T)
    }

}