package com.freisia.vueee.ui.list.tvshow

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.freisia.vueee.model.tv.SearchTV
import com.freisia.vueee.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.tvshows_fragment.*
import kotlinx.android.synthetic.main.tvshows_fragment.loadings
import kotlinx.android.synthetic.main.tvshows_fragment.nfs
import kotlinx.android.synthetic.main.tvshows_fragment.spinners2
import kotlinx.android.synthetic.main.tvshows_fragment.white_views
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TVShowsFragment: Fragment(),CardAdapter.OnLoadMoreListener {

    companion object {
        const val data = "tvshows"
    }

    private val viewModel by viewModel<TVShowsViewModel>()
    private lateinit var cardAdapter: CardAdapter<SearchTV>
    private var detail: ArrayList<SearchTV> = ArrayList()
    private var coroutineJob : Job? = null
    private var check = 1
    private var temp = 0
    private var checkSpinner = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tvshows_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EspressoIdlingResource.increment()
        this.retainInstance = true
        if(nfs.visibility == View.VISIBLE) nfs.visibility = View.GONE
        if(list2.visibility == View.VISIBLE) list2.visibility = View.GONE
        loadings.visibility = View.VISIBLE
        white_views.visibility = View.VISIBLE
        spinnerCheck()
        viewModel.isLoading.observeForever(observeLoading())
        viewModel.isFound.observeForever(observeFound())
        cardGridRecyclerView()
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
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

    private fun spinnerCheck(){
        val adapters = object : ArrayAdapter<String>(this.requireContext(),android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.spinnerTV)){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                view.setTypeface(view.typeface, Typeface.BOLD)
                view.setPadding(12)

                if (position == spinners2.selectedItemPosition){
                    view.background = ColorDrawable(Color.parseColor("#E61401"))
                    view.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                return view

            }

        }
        spinners2.adapter = adapters
        spinners2.gravity = Gravity.CENTER
        spinners2.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                EspressoIdlingResource.increment()
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
                            if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
                                EspressoIdlingResource.decrement()
                            }
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
                            viewModel.getOnAirData()
                            if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
                                EspressoIdlingResource.decrement()
                            }
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
                            if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
                                EspressoIdlingResource.decrement()
                            }
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

    private fun cardGridRecyclerView(){
        list2.itemAnimator = DefaultItemAnimator()
        list2.layoutManager = GridLayoutManager(this.requireActivity(),2)
        cardAdapter = CardAdapter(detail, data, list2)
        cardAdapter.onLoadMoreListener = this
        list2.adapter = cardAdapter
    }
    override fun onGridLoadMore() {
        check++
        coroutineJob?.cancel()
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            viewModel.onLoadMore(checkSpinner)
        }
    }

    private fun notFound() {
        if(list2.visibility == View.VISIBLE){
            list2.visibility = View.GONE
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
        list2.visibility = View.VISIBLE
    }

    private fun getData(){
        cardAdapter.setLoad()
        cardAdapter.setData(detail)
    }

    private fun observeFound() : Observer<Boolean> {
        return Observer {
            if(it){
                viewModel.listData.observeForever(observeData())
            } else {
                notFound()
            }
        }
    }

    private fun observeData() : Observer<List<SearchTV>> {
        return Observer{
            temp++
            if(check == temp){
                detail.addAll(it as ArrayList<SearchTV>)
                found()
                getData()
            } else {
                temp = check
            }
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
