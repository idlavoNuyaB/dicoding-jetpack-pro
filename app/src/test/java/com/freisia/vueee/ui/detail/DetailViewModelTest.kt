package com.freisia.vueee.ui.detail

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.freisia.vueee.ui.daftar.movies.MoviesViewModel
import com.freisia.vueee.ui.daftar.tvshow.TVShowsViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun getData() {
        val alldetailM = MoviesViewModel().getData(context)
        val alldetailT = TVShowsViewModel().getData(context)
        viewModel = DetailViewModel()
        val detailM = viewModel.getData(alldetailM[0])
        val equalM = "A Star is Born"
        val detailT = viewModel.getData(alldetailT[0])
        val equalT = "Arrow"
        assertEquals(equalM, detailM.title)
        assertEquals(equalT,detailT.title)
    }
}