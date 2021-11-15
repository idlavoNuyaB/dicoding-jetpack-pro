package com.freisia.vueee.ui.favorite.tvshow

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.freisia.vueee.R
import com.freisia.vueee.adapter.CardFavoriteAdapter
import com.freisia.vueee.model.tv.TV
import kotlinx.android.synthetic.main.tvshows_favorite_fragment.*
import kotlinx.android.synthetic.main.tvshows_favorite_fragment.view.*


import org.koin.androidx.viewmodel.ext.android.viewModel

class TVShowsFavoriteFragment: Fragment() {

    private val viewModel by viewModel<TVShowsFavoriteViewModel>()
    private lateinit var cardAdapter: CardFavoriteAdapter<TV>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tvshows_favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        this.retainInstance = true
        viewModel.getData().observe(viewLifecycleOwner,observer)
        list4.itemAnimator = DefaultItemAnimator()
        list4.layoutManager = GridLayoutManager(this.context,2)
        cardAdapter = this.activity?.let { CardFavoriteAdapter(it) }!!
        list4.adapter = cardAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_favorite,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                val positiveButtonClick = { _: DialogInterface, _: Int ->
                    viewModel.deleteAll()
                    viewModel.getData().observe(this.viewLifecycleOwner, observer)
                }
                val negativeButtonClick = { _: DialogInterface, _: Int ->
                    Toast.makeText(this.requireContext(), R.string.cancel, Toast.LENGTH_LONG).show()
                }
                val builder = AlertDialog.Builder(this.requireContext())
                with(builder) {
                    setTitle(R.string.deleteTV)
                    setMessage(R.string.messagedeleteTV)
                    setPositiveButton(
                        R.string.yes,
                        DialogInterface.OnClickListener(positiveButtonClick)
                    )
                    setNegativeButton(R.string.no, negativeButtonClick)
                    val dialog = create()
                    dialog.show()
                    val btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    val btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                    val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = 10f
                    btnPositive.layoutParams = layoutParams
                    btnNegative.layoutParams = layoutParams
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val observer = Observer<PagedList<TV>>{
        if(it != null){
            cardAdapter.submitList(it)
        }
    }
}
