package com.freisia.vueee.data.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freisia.vueee.data.remote.APIService
import com.freisia.vueee.utils.Constant
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MovieRepositoryTest {

    private lateinit var apiService: APIService
    private lateinit var repositoryM: MovieRepository
    private val id = 400160
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        apiService = network()
        repositoryM = MovieRepository(apiService)
    }

    @Test
     fun getDetail() = runBlocking {
        val response = repositoryM.getDetail(id)
        assertEquals("The SpongeBob Movie: Sponge on the Run", response.body()?.title)
    }

    @Test
    fun getList() = runBlocking{
        val response = repositoryM.getList(1)
        assertEquals(20, response.body()?.result?.size)
    }

    @Test
    fun getNowPlaying() = runBlocking {
        val response = repositoryM.getNowPlayingList(1)
        assertEquals(20, response.body()?.result?.size)
    }

    @Test
    fun getTopRated() = runBlocking {
        val response = repositoryM.getTopList(1)
        assertEquals(20, response.body()?.result?.size)
    }

    @After
    fun tearDown(){
        stopKoin()
    }

    private fun network() : APIService{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .build()
        return retrofit.create(APIService::class.java)
    }
}