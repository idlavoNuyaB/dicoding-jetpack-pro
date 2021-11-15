package com.freisia.vueee.ui.daftar.movies

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun getData() {
        viewModel = MoviesViewModel()
        val detail = viewModel.getData(context)
        val equal = 19
        assertEquals(equal, detail.size)
    }
}