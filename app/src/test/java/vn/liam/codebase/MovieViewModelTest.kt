package vn.liam.codebase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.liam.codebase.base.repositories.MovieRepository
import vn.liam.codebase.base.services.MovieServices
import vn.liam.codebase.ui.movie.MovieViewModel

internal class MovieViewModelTest : TestBase() {

    @Mock
    lateinit var movieServices: MovieServices

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: MovieViewModel

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieServices = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieServices::class.java)

        movieRepository = MovieRepository(movieServices, appDb.detailsCachedDao())
        viewModel = MovieViewModel(movieRepository, savedStateHandle, appDb)
        viewModel.searchQuery = MutableLiveData("Spy")
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getSearchQuery(): Unit = runTest {
        mockWebServer.enqueue(
            getResponse("search_response.json")
        )
        val res = viewModel.getMovies().firstOrNull()
        assertTrue(res != null)

    }

    @Test
    fun setSearchQuery() = runBlocking {
        val query = "Spy"
        assertTrue(viewModel.searchQuery.value == query)
    }

    @Test
    fun getMovies(): Unit = runTest {
        mockWebServer.enqueue(
            getResponse("response_success.json")
        )
        val res = viewModel.getMovies().firstOrNull()
        assertTrue(res != null)
    }

}