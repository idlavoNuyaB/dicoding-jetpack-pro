package com.freisia.vueee.data.repository

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
class TVRepositoryTest {

    private lateinit var repository: TVRepository
    private lateinit var apiService: APIService

    private val id = 82856
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        apiService = network()
        repository = TVRepository(apiService)
    }

    @Test
    fun getDetail() = runBlocking {
        val response = repository.getDetail(id)
        assertEquals("The Mandalorian", response.body()?.original_name)
    }

    @Test
    fun getList() = runBlocking{
        val response = repository.getList(1)
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

    @Test
    fun getRating() = runBlocking {
        val response = repository.getRating(id)
        for(i in 0 until response.body()?.results?.size!!){
            if(response.body()!!.results[i].iso_3166_1 == "US"){
                assertEquals("TV-14", response.body()?.results!![i].rating)
                break
            }
        }
    }
}