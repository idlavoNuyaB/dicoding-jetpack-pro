package com.freisia.vueee.ui.daftar.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.freisia.vueee.R
import com.freisia.vueee.adapter.CardAdapter
import com.freisia.vueee.model.Movie
import kotlinx.android.synthetic.main.tvshows_fragment.*

class TVShowsFragment : Fragment() {

    companion object {
        const val data = "tvshows"
    }

    private lateinit var viewModel: TVShowsViewModel
    private lateinit var cardAdapter: CardAdapter
    private var detail: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tvshows_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TVShowsViewModel::class.java)
        detail = viewModel.getData(this.requireContext())
        cardGridRecyclerView()
    }

    private fun cardGridRecyclerView(){
        list2.itemAnimator = DefaultItemAnimator()
        list2.layoutManager = GridLayoutManager(this.requireActivity(),2)
        cardAdapter = CardAdapter(detail, data)
        list2.adapter = cardAdapter
    }
}
