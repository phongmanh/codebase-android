package vn.liam.codebase.base.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manhnguyen.codebase.data.converters.DateConverter
import com.manhnguyen.codebase.data.converters.IntListConverter
import com.manhnguyen.codebase.data.converters.StringListConverter
import vn.liam.codebase.base.database.AppDatabase.Companion.DB_VERSION
import vn.liam.codebase.base.database.converters.EntityConverters
import vn.liam.codebase.base.database.dao.DetailsCachedDAO
import vn.liam.codebase.base.database.dao.MovieDAO
import vn.liam.codebase.base.database.dao.RemoteKeyDao
import vn.liam.codebase.base.models.MovieDetailsCached
import vn.liam.codebase.base.models.MovieModel
import vn.liam.codebase.base.models.RemoteKey

@Database(
    entities = [MovieModel::class, MovieDetailsCached::class, RemoteKey::class],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(
    StringListConverter::class,
    IntListConverter::class,
    DateConverter::class,
    EntityConverters::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDAO

    abstract fun detailsCachedDao(): DetailsCachedDAO

    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object : SingletonProvider<AppDatabase, Context> {
        const val DB_NAME = "movie_database"
        const val DB_VERSION = 1
        override val singletonInstance = SingletonInstance<AppDatabase, Context> {
            Room.databaseBuilder(it.applicationContext, AppDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
        }

    }

}

interface SingletonProvider<out T, in A> {
    val singletonInstance: SingletonInstance<T, A>
    fun getInstance(arg: A) = singletonInstance.getInstance(arg)
}

class SingletonInstance<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}