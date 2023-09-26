package vn.liam.codebase

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import vn.liam.codebase.base.database.AppDatabase
import vn.liam.codebase.utils.FileReader
import java.util.concurrent.TimeUnit


abstract class TestBase {

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    val mockWebServer = MockWebServer()
    lateinit var appDb: AppDatabase

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockWebServer.start()
        val context = Mockito.mock(Context::class.java)
        appDb = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
    }

    @AfterEach
    fun finish() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }


    protected fun getResponse(jsonFileName: String): MockResponse {
        return MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache").setResponseCode(200)
            .setBody(FileReader.readStringFromFile(jsonFileName))
    }

    private val dispatcher: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            when (request.path) {
                "/api-url-one" -> return MockResponse().setResponseCode(201)

                "/api-url-two" -> return MockResponse().setHeader("x-header-name", "header-value")
                    .setResponseCode(200).setBody("<response />")

                "/api-url-three" -> return MockResponse().setResponseCode(500)
                    .setBodyDelay(5000, TimeUnit.SECONDS).setChunkedBody("<error-response />", 5)

                "/api-url-four" -> return MockResponse().setResponseCode(200)
                    .setBody("{\"data\":\"\"}").throttleBody(1024, 5, TimeUnit.SECONDS)
            }
            return MockResponse().setResponseCode(404)
        }
    }

}