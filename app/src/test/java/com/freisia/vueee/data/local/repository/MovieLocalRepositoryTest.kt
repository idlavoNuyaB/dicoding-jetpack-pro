package com.freisia.vueee.data.local.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.utils.DataDummy
import com.freisia.vueee.utils.PagedListUtil
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MovieLocalRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var repository: MovieLocalRepository

    @Before
    fun setUp() {
        repository = mock(MovieLocalRepository::class.java)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getDatas() {
        val data = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
        `when`(repository.getDatas()).thenReturn(data)
        repository.getDatas()
        verify(repository).getDatas()
        val list = ArrayList<Movie>()
        list.add(DataDummy.getMovies())
        val movies = PagedListUtil.mockPagedList(list)
        assertNotNull(movies)
        assertEquals(1,movies.size)
    }

    @Test
    fun getDataDetails() {
        val data = MutableLiveData<List<Movie>>()
        val list = ArrayList<Movie>()
        list.add(DataDummy.getMovies())
        data.value = list
        `when`(repository.getDataDetails()).thenReturn(data)
        repository.getDataDetails()
        verify(repository).getDataDetails()
        assertNotNull(data)
        assertEquals(1, (data.value as ArrayList<Movie>).size)
    }

    @Test
    fun insert() {
        val data = DataDummy.getMovies()
        val id = data.id
        runBlocking {
            `when`(repository.insert(data)).thenReturn(id.toLong())
            repository.insert(data)
            verify(repository).insert(data)
            assertNotNull(id)
            assertEquals(400160,id)
        }
    }

    @Test
    fun delete() {
        runBlocking {
            val data = DataDummy.getMovies()
            val id = data.id
            `when`(repository.insert(data)).thenReturn(id.toLong())
            repository.insert(data)
            repository.delete(data)
            verify(repository).delete(data)
        }
    }

    @Test
    fun deleteAll() {
        runBlocking {
            val data = DataDummy.getMovies()
            val id = data.id
            `when`(repository.insert(data)).thenReturn(id.toLong())
            repository.insert(data)
            repository.deleteAll()
            verify(repository).deleteAll()
        }
    }
}