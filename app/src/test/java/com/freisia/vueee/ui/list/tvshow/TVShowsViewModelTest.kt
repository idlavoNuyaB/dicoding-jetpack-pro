package com.freisia.vueee.ui.list.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freisia.vueee.data.remote.repository.TVRepository
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TVShowsViewModelTest {

    private lateinit var viewModel: TVShowsViewModel
    private lateinit var repository: TVRepository
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repository = mock(TVRepository::class.java)
        viewModel = TVShowsViewModel(repository)
    }

    @Test
    fun getData() {
        viewModel.getData()
        viewModel.isFound.observeForever{
            if(it){
                viewModel.listData.observeForever{ its ->
                    assertEquals(20,its.size)
                }
            }
        }
    }

    @Test
    fun getTopRated(){
        viewModel.getTopRated()
        viewModel.isFound.observeForever{
            if(it){
                viewModel.listData.observeForever{ its ->
                    assertEquals(20,its.size)
                }
            }
        }
    }

    @Test
    fun onAir(){
        viewModel.getOnAirData()
        viewModel.isFound.observeForever{
            if(it){
                viewModel.listData.observeForever{ its ->
                    assertEquals(20,its.size)
                }
            }
        }
    }

    @Test
    fun onLoadMore(){
        viewModel.onLoadMore(1)
        viewModel.isFound.observeForever{
            if(it){
                viewModel.listData.observeForever{ its ->
                    assertEquals(20,its.size)
                }
            }
        }
    }

    @After
    fun tearDown(){
        stopKoin()
    }
}