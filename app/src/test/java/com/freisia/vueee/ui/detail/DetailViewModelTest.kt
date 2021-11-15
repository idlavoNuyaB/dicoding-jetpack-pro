package com.freisia.vueee.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freisia.vueee.data.repository.MovieRepository
import com.freisia.vueee.data.repository.TVRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.tv.TV
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class DetailViewModelTest {
    private lateinit var viewModelM: DetailViewModel<MovieRepository, Movie>
    private lateinit var viewModelT: DetailViewModel<TVRepository, TV>
    private lateinit var repositoryT: TVRepository
    private lateinit var repositoryM: MovieRepository
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repositoryT = Mockito.mock(TVRepository::class.java)
        repositoryM = Mockito.mock(MovieRepository::class.java)
        viewModelM = DetailViewModel(repositoryM)
        viewModelT = DetailViewModel(repositoryT)
    }

    @Test
    fun getData() {
        viewModelM.getData(400160)
        viewModelT.getData(82856)
        viewModelT.listData.observeForever{
            assertEquals("The Mandalorian",it.original_name)
        }
        viewModelM.listData.observeForever{
            assertEquals("The SpongeBob Movie: Sponge on the Run",it.original_title)
        }
    }

    @After
    fun tearDown(){
        stopKoin()
    }
}