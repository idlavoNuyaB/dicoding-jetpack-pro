package com.freisia.vueee.ui.favorite.movies

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.freisia.vueee.R
import com.freisia.vueee.adapter.CardFavoriteAdapter
import com.freisia.vueee.model.movie.Movie
import kotlinx.android.synthetic.main.movies_favorite_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFavoriteFragment : Fragment() {

    private val favoriteViewModel: MoviesFavoriteViewModel by viewModel()
    private lateinit var cardAdapter: CardFavoriteAdapter<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_favorite_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        this.retainInstance = true
        favoriteViewModel.getData().observe(this.viewLifecycleOwner,observer)
        list3.itemAnimator = DefaultItemAnimator()
        list3.layoutManager = GridLayoutManager(this.context,2)
        cardAdapter = this.activity?.let { CardFavoriteAdapter(it) }!!
        list3.adapter = cardAdapter
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
                    favoriteViewModel.deleteAll()
                    favoriteViewModel.getData().observe(this.viewLifecycleOwner, observer)
                }
                val negativeButtonClick = { _: DialogInterface, _: Int ->
                    Toast.makeText(this.requireContext(), R.string.cancel, Toast.LENGTH_LONG).show()
                }
                val builder = AlertDialog.Builder(this.requireContext())
                with(builder) {
                    setTitle(R.string.delete)
                    setMessage(R.string.messagedelete)
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

    private val observer = Observer<PagedList<Movie>>{
        if(it != null){
            cardAdapter.submitList(it)
        }
    }


}
