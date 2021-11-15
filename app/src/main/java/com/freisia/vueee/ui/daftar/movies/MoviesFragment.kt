package com.freisia.vueee.ui.daftar.movies

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
import kotlinx.android.synthetic.main.movies_fragment.*

class MoviesFragment : Fragment() {

    companion object {
        const val data = "movies"
    }

    private lateinit var viewModel: MoviesViewModel
    private lateinit var cardAdapter: CardAdapter
    private var detail: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.retainInstance = true
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        detail = viewModel.getData(this.requireContext())
        cardGridRecyclerView()
    }

    private fun cardGridRecyclerView(){
        list.itemAnimator = DefaultItemAnimator()
        list.layoutManager = GridLayoutManager(this.requireActivity(),2)
        cardAdapter = CardAdapter(detail, data)
        list.adapter = cardAdapter
    }

}
