package vn.liam.codebase

import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.liam.codebase.base.networking.Resource
import vn.liam.codebase.base.repositories.MovieRepository
import vn.liam.codebase.base.services.MovieServices


internal class MovieRepositoryTest : TestBase() {

    lateinit var movieServices: MovieServices

    lateinit var repository: MovieRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieServices = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieServices::class.java)

        repository = MovieRepository(movieServices, appDb.detailsCachedDao())
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getTrendingMovies() = runBlocking {
        mockWebServer.enqueue(
            getResponse("response_success.json")
        )
        val res = repository.getTrendingMovies(1, 15)
        assertTrue(res is Resource.Success)
        assertTrue(res.data!!.size == 20)
        assertTrue(!res.data.isNullOrEmpty())
    }

    @Test
    fun searchMovies() = runBlocking {
        mockWebServer.enqueue(
            getResponse("search_response.json")
        )
        val res = repository.searchMovies("No one", 1, 15)
        assertTrue(res is Resource.Success)
        assertTrue(!res.data.isNullOrEmpty())
        assertTrue(res.data!!.size == 20)
    }

    @Test
    fun getMovieById() = runBlocking {
//        val movieId = 820609
//        mockWebServer.enqueue(
//            getResponse("movie_byId_response.json")
//        )
//        val res = repository.getMovieById(movieId)
//        assertTrue(res is Resource.Success)
//        assertTrue(res.data!!.title == "No One Will Save You")
    }
}