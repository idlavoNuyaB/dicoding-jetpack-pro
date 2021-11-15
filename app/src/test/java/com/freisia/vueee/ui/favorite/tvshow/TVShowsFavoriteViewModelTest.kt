package com.freisia.vueee.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import com.freisia.vueee.data.local.MovieDBDatabase
import com.freisia.vueee.data.local.dao.TVDao
import com.freisia.vueee.data.local.repository.TVLocalRepository
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.utils.DataDummy
import com.freisia.vueee.utils.ListDataSource
import com.freisia.vueee.utils.PagedListUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*

import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@ExperimentalCoroutinesApi
class TVShowsFavoriteViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var repository: TVLocalRepository
    private lateinit var viewModel: TVShowsFavoriteViewModel
    private lateinit var tvDao: TVDao
    private lateinit var movieDBDatabase: MovieDBDatabase

    @Before
    fun setUp() {
        movieDBDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),MovieDBDatabase::class.java).allowMainThreadQueries().build()
        tvDao = movieDBDatabase.tvDao()
        repository = mock(TVLocalRepository::class.java)
        viewModel = TVShowsFavoriteViewModel(repository)
    }

    @After
    fun tearDown() {
        stopKoin()
        movieDBDatabase.close()
    }

    @Test
    fun getData() {
        val data = DataDummy.getTV()
        val list = ArrayList<TV>()
        list.add(data)
        val response = PagedListUtil.mockPagedList(list)
        val movie = MutableLiveData<PagedList<TV>>()
        movie.value = response
        val dataSource = ListDataSource(list)
        `when`(repository.getDatas()).thenReturn(dataSource)
        repository.getDatas()
        verify(repository).getDatas()
        viewModel.getData().observeForever{
            Assert.assertNotNull(it)
            Assert.assertEquals(1, it.size)
        }

    }

    @Test
    fun deleteAll() {
        runBlocking {
            val data = DataDummy.getTV()
            tvDao.insert(data)
        }
        var dataFromDb = tvDao.findAllTV()
        Assert.assertNotNull(dataFromDb)
        viewModel.deleteAll()
        dataFromDb = tvDao.findAllTV()
        Assert.assertNull(dataFromDb.value)
    }
}