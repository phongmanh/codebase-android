package vn.liam.codebase

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.liam.codebase.base.models.RemoteKey
import vn.liam.codebase.base.pagingsources.MovieRemoteMediator
import vn.liam.codebase.base.repositories.MovieRepository
import vn.liam.codebase.base.services.MovieServices
import vn.liam.codebase.ui.movie.MovieSdk
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

internal class MovieRemoteMediatorTest : TestBase() {

    private lateinit var movieRemoteMediator: MovieRemoteMediator
    lateinit var movieServices: MovieServices
    lateinit var movieRepository: MovieRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieServices = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieServices::class.java)
        movieRepository = MovieRepository(movieServices, appDb.detailsCachedDao())
        movieRemoteMediator = MovieRemoteMediator(movieRepository, appDb)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun load() {

    }

    @Test
    fun initialize_validCaching() = runTest {

        val date1 = "2023-10-26T15:00:00Z"
        assertTrue(caching_validate(date1))

        // Caching expired
        val date = Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(6))
        val strDate = createDate(date)
        assertTrue(!caching_validate(strDate))

    }

    private fun caching_validate(dateTime: String): Boolean {
        val key = 820609
        val date: Date = createDate(dateTime)
        val remoteKey = RemoteKey(key, null, 3, date.time)
        val cacheTimeout =
            TimeUnit.MILLISECONDS.convert(MovieSdk.PARAMS.CACHED_TIMEOUT, TimeUnit.MINUTES)
        return (System.currentTimeMillis() - remoteKey.timestamp) <= cacheTimeout
    }

    private fun createDate(dateTime: Date): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return df.format(dateTime)
    }

    private fun createDate(dateTime: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        try {
            return format.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }

}