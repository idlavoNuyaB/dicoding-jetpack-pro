package com.freisia.vueee.ui.daftar.tvshow

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TVShowsViewModelTest {

    private lateinit var tvShowsViewModel: TVShowsViewModel
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun getData() {
        tvShowsViewModel = TVShowsViewModel()
        val detail = tvShowsViewModel.getData(context)
        val equal = 20
        assertEquals(equal,detail.size)
    }
}