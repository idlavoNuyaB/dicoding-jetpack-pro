package com.freisia.vueee.ui.favorite.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import com.freisia.vueee.data.local.MovieDBDatabase
import com.freisia.vueee.data.local.dao.MovieDao
import com.freisia.vueee.data.local.repository.MovieLocalRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.utils.DataDummy
import com.freisia.vueee.utils.ListDataSource
import com.freisia.vueee.utils.PagedListUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@ExperimentalCoroutinesApi
class MoviesFavoriteViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MoviesFavoriteViewModel
    private lateinit var repository: MovieLocalRepository
    private lateinit var movieDao: MovieDao
    private lateinit var movieDBDatabase: MovieDBDatabase

    @Before
    fun setUp() {
        movieDBDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),MovieDBDatabase::class.java).allowMainThreadQueries().build()
        movieDao = movieDBDatabase.movieDao()
        repository = mock(MovieLocalRepository::class.java)
        viewModel = MoviesFavoriteViewModel(repository)
    }

    @After
    fun tearDown() {
        stopKoin()
        movieDBDatabase.close()
    }

    @Test
    fun getData() {
        val data = DataDummy.getMovies()
        val list = ArrayList<Movie>()
        list.add(data)
        val response = PagedListUtil.mockPagedList(list)
        val movie = MutableLiveData<PagedList<Movie>>()
        movie.value = response
        val dataSource = ListDataSource(list)
        `when`(repository.getDatas()).thenReturn(dataSource)
        repository.getDatas()
        verify(repository).getDatas()
        viewModel.getData().observeForever{
            assertNotNull(it)
            assertEquals(1, it.size)
        }

    }

    @Test
    fun deleteAll() {
        runBlocking {
            val data = DataDummy.getMovies()
            movieDao.insert(data)
        }
        var dataFromDb = movieDao.findAllMovie()
        assertNotNull(dataFromDb)
        viewModel.deleteAll()
        dataFromDb = movieDao.findAllMovie()
        assertNull(dataFromDb.value)
    }
}