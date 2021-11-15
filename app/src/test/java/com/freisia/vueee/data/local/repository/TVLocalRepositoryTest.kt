package com.freisia.vueee.data.local.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.utils.DataDummy
import com.freisia.vueee.utils.PagedListUtil
import junit.framework.Assert
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
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TVLocalRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var repository: TVLocalRepository

    @Before
    fun setUp() {
        repository = mock(TVLocalRepository::class.java)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getDatas() {
        val data = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TV>
        Mockito.`when`(repository.getDatas()).thenReturn(data)
        repository.getDatas()
        Mockito.verify(repository).getDatas()
        val list = ArrayList<TV>()
        list.add(DataDummy.getTV())
        val movies = PagedListUtil.mockPagedList(list)
        assertNotNull(movies)
        assertEquals(1, movies.size)
    }

    @Test
    fun getDataDetails() {
        val data = MutableLiveData<List<TV>>()
        val list = ArrayList<TV>()
        list.add(DataDummy.getTV())
        data.value = list
        Mockito.`when`(repository.getDataDetail()).thenReturn(data)
        repository.getDataDetail()
        Mockito.verify(repository).getDataDetail()
        assertNotNull(data)
        assertEquals(1, (data.value as ArrayList<TV>).size)
    }

    @Test
    fun insert() {
        val data = DataDummy.getTV()
        val id = data.id
        runBlocking {
            Mockito.`when`(repository.insert(data)).thenReturn(id.toLong())
            repository.insert(data)
            Mockito.verify(repository).insert(data)
            assertNotNull(id)
            assertEquals(82856,id)
        }
    }

    @Test
    fun delete() {
        runBlocking {
            val data = DataDummy.getTV()
            val id = data.id
            Mockito.`when`(repository.insert(data)).thenReturn(id.toLong())
            repository.insert(data)
            repository.delete(data)
            Mockito.verify(repository).delete(data)
        }
    }

    @Test
    fun deleteAll() {
        runBlocking {
            val data = DataDummy.getTV()
            val id = data.id
            Mockito.`when`(repository.insert(data)).thenReturn(id.toLong())
            repository.insert(data)
            repository.deleteAll()
            Mockito.verify(repository).deleteAll()
        }
    }
}