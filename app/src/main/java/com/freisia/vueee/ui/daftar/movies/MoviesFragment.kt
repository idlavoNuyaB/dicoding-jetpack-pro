package com.freisia.vueee.ui.daftar.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.freisia.vueee.R
import com.freisia.vueee.adapter.CardAdapter
import com.freisia.vueee.model.movie.SearchMovie
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment(),CardAdapter.OnLoadMoreListener {

    companion object {
        const val data = "movies"
    }

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var cardAdapter: CardAdapter<SearchMovie>
    private var detail: ArrayList<SearchMovie> = ArrayList()
    private var coroutineJob : Job? = null
    private var check = 1
    private var temp = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutineJob?.cancel()
        this.retainInstance = true
        if(nfs.visibility == View.VISIBLE) nfs.visibility = View.GONE
        if(list.visibility == View.VISIBLE) list.visibility = View.GONE
        loadings.visibility = View.VISIBLE
        white_views.visibility = View.VISIBLE
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            viewModel.getData()
        }
        viewModel.isLoading.observeForever(observeLoading())
        viewModel.isFound.observeForever(observeFound())
        cardGridRecyclerView()
    }

    override fun onPause() {
        coroutineJob?.cancel()
        viewModel.isFound.removeObserver(observeFound())
        viewModel.isLoading.removeObserver(observeLoading())
        viewModel.listData.removeObserver(observeData())
        super.onPause()
    }

    override fun onDestroy() {
        coroutineJob?.cancel()
        viewModel.isFound.removeObserver(observeFound())
        viewModel.isLoading.removeObserver(observeLoading())
        viewModel.listData.removeObserver(observeData())
        super.onDestroy()
    }

    private fun cardGridRecyclerView(){
        list.itemAnimator = DefaultItemAnimator()
        list.layoutManager = GridLayoutManager(this.requireActivity(),2)
        cardAdapter = CardAdapter(detail, data, list)
        cardAdapter.onLoadMoreListener = this
        list.adapter = cardAdapter
    }

    override fun onGridLoadMore() {
        check++
        coroutineJob?.cancel()
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            viewModel.onLoadMore()
        }
    }

    private fun notFound() {
        if(list.visibility == View.VISIBLE){
            list.visibility = View.GONE
        }
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
        list.visibility = View.VISIBLE
    }

    private fun getData(){
        cardGridRecyclerView()
        cardAdapter.setLoad()
        cardAdapter.setData(detail)
    }

    private fun observeFound() : Observer<Boolean>{
        return Observer {
            if(it){
                viewModel.listData.observeForever(observeData())
            } else {
                notFound()
            }
        }
    }

    private fun observeData() : Observer<List<SearchMovie>>{
        return Observer{
            temp++
            if(check == temp){
                detail.addAll(it as ArrayList<SearchMovie>)
                found()
                getData()
            } else {
                temp = check
            }
        }
    }

    private fun observeLoading() : Observer<Boolean>{
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
