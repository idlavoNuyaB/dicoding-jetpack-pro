package com.freisia.vueee.ui.daftar.movies


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freisia.vueee.data.repository.MovieRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var repository: MovieRepository
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repository = mock(MovieRepository::class.java)
        viewModel = MoviesViewModel(repository)
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
    fun onLoadMore(){
        viewModel.onLoadMore()
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