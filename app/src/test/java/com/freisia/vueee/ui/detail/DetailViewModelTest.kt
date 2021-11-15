package com.freisia.vueee.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freisia.vueee.data.local.repository.MovieLocalRepository
import com.freisia.vueee.data.local.repository.TVLocalRepository
import com.freisia.vueee.data.remote.repository.MovieRepository
import com.freisia.vueee.data.remote.repository.TVRepository
import com.freisia.vueee.model.all.Genres
import com.freisia.vueee.model.movie.Countries
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.movie.Releases
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.utils.DataDummy
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class DetailViewModelTest {
    private lateinit var viewModelM: DetailViewModel<MovieRepository, Movie,MovieLocalRepository>
    private lateinit var viewModelT: DetailViewModel<TVRepository, TV, TVLocalRepository>
    private lateinit var repositoryT: TVRepository
    private lateinit var repositoryM: MovieRepository
    private lateinit var localRepoT: TVLocalRepository
    private lateinit var localRepoM: MovieLocalRepository
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repositoryT = Mockito.mock(TVRepository::class.java)
        repositoryM = Mockito.mock(MovieRepository::class.java)
        localRepoT = Mockito.mock(TVLocalRepository::class.java)
        localRepoM = Mockito.mock(MovieLocalRepository::class.java)
        viewModelM = DetailViewModel(repositoryM,localRepoM)
        viewModelT = DetailViewModel(repositoryT,localRepoT)
    }

    @Test
    fun getData() {
        viewModelM.getData(400160)
        viewModelT.getData(82856)
        viewModelT.listData.observeForever{
            assertEquals("The Mandalorian",it.name)
        }
        viewModelM.listData.observeForever{
            assertEquals("The SpongeBob Movie: Sponge on the Run",it.title)
        }
    }

    @Test
    fun insert(){
        viewModelT.insert(DataDummy.getTV())
        viewModelM.insert(DataDummy.getMovies())
        viewModelT.localData?.observeForever{
            assertEquals("The Mandalorian",it[0].name)
        }
        viewModelM.localData?.observeForever{
            assertEquals("The SpongeBob Movie: Sponge on the Run",it[0].title)
        }
    }

    @Test
    fun delete(){
        viewModelT.insert(DataDummy.getTV())
        viewModelM.insert(DataDummy.getMovies())
        viewModelT.delete(DataDummy.getTV())
        viewModelM.delete(DataDummy.getMovies())
        viewModelT.localData?.observeForever{
            assertNull(it)
        }
        viewModelM.localData?.observeForever{
            assertNull(it)
        }

    }

    @After
    fun tearDown(){
        stopKoin()
    }
}