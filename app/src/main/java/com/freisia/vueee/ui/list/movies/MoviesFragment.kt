package com.freisia.vueee.ui.list.movies

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.setPadding
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
    private var checkSpinner = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.retainInstance = true
        if(nfs.visibility == View.VISIBLE) nfs.visibility = View.GONE
        if(list.visibility == View.VISIBLE) list.visibility = View.GONE
        loadings.visibility = View.VISIBLE
        white_views.visibility = View.VISIBLE
        spinnerCheck()
        viewModel.isLoading.observeForever(observeLoading())
        viewModel.isFound.observeForever(observeFound())
        cardGridRecyclerView()
    }

    private fun spinnerCheck(){
        val adapters = object : ArrayAdapter<String>(this.requireContext(),android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.spinner)){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view:TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                view.setTypeface(view.typeface, Typeface.BOLD)
                view.setPadding(12)

                if (position == spinner.selectedItemPosition){
                    view.background = ColorDrawable(Color.parseColor("#E61401"))
                    view.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                return view

            }

        }
        spinner.adapter = adapters
        spinner.gravity = Gravity.CENTER
        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {
                        if(!detail.isNullOrEmpty()){
                            cardAdapter.resetData()
                            detail = ArrayList()
                        }
                        coroutineJob?.cancel()
                        viewModel.reset()
                        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                            viewModel.getData()
                        }
                        checkSpinner = 1
                        viewModel.isLoading.observeForever(observeLoading())
                        viewModel.isFound.observeForever(observeFound())
                    }
                    1 -> {
                        if(!detail.isNullOrEmpty()) {
                            cardAdapter.resetData()
                            detail = ArrayList()
                        }
                        coroutineJob?.cancel()
                        viewModel.reset()
                        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                            viewModel.getNowPlaying()
                        }
                        checkSpinner = 2
                        viewModel.isLoading.observeForever(observeLoading())
                        viewModel.isFound.observeForever(observeFound())
                    }
                    2 -> {
                        if(!detail.isNullOrEmpty()) {
                            cardAdapter.resetData()
                            detail = ArrayList()
                        }
                        coroutineJob?.cancel()
                        viewModel.reset()
                        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
                            viewModel.getTopRated()
                        }
                        checkSpinner = 3
                        viewModel.isLoading.observeForever(observeLoading())
                        viewModel.isFound.observeForever(observeFound())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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
            viewModel.onLoadMore(checkSpinner)
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
